package egovframework.let.cms.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String status;
    private String roleType;
} 