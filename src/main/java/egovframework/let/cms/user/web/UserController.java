package egovframework.let.cms.user.web;

import egovframework.com.cmm.response.ApiResponse;
import egovframework.let.cms.user.domain.Cms05User;
import egovframework.let.cms.user.dto.PasswordChangeDto;
import egovframework.let.cms.user.dto.SiteInfo;
import egovframework.let.cms.user.dto.SiteManagerRegisterRequest;
import egovframework.let.cms.user.dto.UserDto;
import egovframework.let.cms.user.dto.UserRegisterRequest;
import egovframework.let.cms.user.exception.UserNotFoundException;
import egovframework.let.cms.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cms_05_User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Validated @RequestBody UserDto userDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.createUser(userDto)));
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @Parameter(description = "User ID") @PathVariable String userId,
            @Validated @RequestBody UserDto userDto) {
        userDto.setUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(userDto)));
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID") @PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.successVoid("User deleted successfully"));
    }

    @Operation(summary = "Get a user by ID")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(
            @Parameter(description = "User ID") @PathVariable String userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(ApiResponse.success(user)))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Operation(summary = "Get all users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUsers(pageable)));
    }

    @Operation(summary = "Change user password")
    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Parameter(description = "User ID") @PathVariable String userId,
            @Validated @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(userId, passwordChangeDto.getNewPassword());
        return ResponseEntity.ok(ApiResponse.successVoid("Password changed successfully"));
    }

    @Operation(summary = "Update user status")
    @PutMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> updateStatus(
            @Parameter(description = "User ID") @PathVariable String userId,
            @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateStatus(userId, status)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody UserRegisterRequest request) {
        Cms05User user = userService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success(convertToDto(user)));
    }

    @PostMapping("/site-managers")
    public ResponseEntity<ApiResponse<UserDto>> registerSiteManager(@RequestBody SiteManagerRegisterRequest request) {
        Cms05User user = userService.registerSiteManager(request);
        return ResponseEntity.ok(ApiResponse.success(convertToDto(user)));
    }

    @GetMapping("/site-info")
    public ResponseEntity<ApiResponse<SiteInfo>> getSiteInfo() {
        return ResponseEntity.ok(ApiResponse.success(userService.getSiteInfo()));
    }

    private UserDto convertToDto(Cms05User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId().toString());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());
        return dto;
    }
} 