package egovframework.let.cms.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SiteInfo {
    private String siteName;
    private String siteDescription;
    private String siteUrl;
    private LocalDateTime lastUpdated;
} 
 
 
 