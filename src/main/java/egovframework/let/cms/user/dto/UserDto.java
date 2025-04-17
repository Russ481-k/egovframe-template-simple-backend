package egovframework.let.cms.user.dto;

import egovframework.let.cms.user.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {
    
    private String userId;
    
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
    
    @NotBlank(message = "Name is required")
    @Size(max = 60, message = "Name must not exceed 60 characters")
    private String name;
    
    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;
    
    @Pattern(regexp = "^[0-9-+()]*$", message = "Phone number should contain only numbers, -, +, and ()")
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
    
    private UserRole role;
    
    private String status;
} 