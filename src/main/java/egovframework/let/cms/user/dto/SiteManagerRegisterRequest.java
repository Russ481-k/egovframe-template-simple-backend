package egovframework.let.cms.user.dto;

import lombok.Data;

@Data
public class SiteManagerRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String siteName;
    private String siteDescription;
    private String siteUrl;
} 
 
 
 