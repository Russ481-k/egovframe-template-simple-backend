package egovframework.let.cms.template.service.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TemplateDto {
    private Long id;
    
    @NotBlank(message = "템플릿명은 필수 입력값입니다.")
    @Size(max = 100, message = "템플릿명은 100자 이내여야 합니다.")
    private String templateName;
    
    @NotBlank(message = "템플릿 HTML은 필수 입력값입니다.")
    private String templateHtml;
    
    @Size(max = 500, message = "템플릿 설명은 500자 이내여야 합니다.")
    private String description;
    
    private String templateType;  // LAYOUT, COMPONENT, PAGE 등
    
    private boolean isDefault;    // 기본 템플릿 여부
    
    private String deviceType;    // PC, MOBILE, RESPONSIVE
    
    // 컴포넌트 정보
    private String headerComponent;
    private String footerComponent;
    private String sidebarComponent;
    
    // 스타일 정보
    private String css;
    private String javascript;
    
    // 메타 정보
    private String createdBy;
    private String updatedBy;
} 