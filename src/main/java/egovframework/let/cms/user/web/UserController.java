package egovframework.let.cms.user.web;

import egovframework.let.cms.user.domain.User;
import egovframework.let.cms.user.dto.PasswordChangeDto;
import egovframework.let.cms.user.dto.UserDto;
import egovframework.let.cms.user.exception.UserNotFoundException;
import egovframework.let.cms.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cms05User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@Validated @RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID") @PathVariable String userId,
            @Validated @RequestBody UserDto userDto) {
        userDto.setUserId(userId);
        User user = convertToEntity(userDto);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") @PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a user by ID")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "User ID") @PathVariable String userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Operation(summary = "Get all users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<User>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @Operation(summary = "Change user password")
    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "User ID") @PathVariable String userId,
            @Validated @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(userId, passwordChangeDto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update user status")
    @PutMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateStatus(
            @Parameter(description = "User ID") @PathVariable String userId,
            @RequestParam String status) {
        User updatedUser = userService.updateStatus(userId, status);
        return ResponseEntity.ok(updatedUser);
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setStatus(userDto.getStatus());
        return user;
    }
} 