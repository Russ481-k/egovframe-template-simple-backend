package egovframework.let.cms.user.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String roleType; // USER, SITE_MANAGER, ADMIN
} 
 
 
 