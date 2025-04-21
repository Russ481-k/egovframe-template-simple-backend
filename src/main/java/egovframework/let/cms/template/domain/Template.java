package egovframework.let.cms.template.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LET_CMS_TEMPLATE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String templateName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String templateHtml;

    @Column(length = 500)
    private String description;

    @Column(name = "template_type", length = 50)
    private String templateType;

    @Column(name = "device_type", length = 20)
    private String deviceType;

    @Column(name = "header_component", columnDefinition = "TEXT")
    private String headerComponent;

    @Column(name = "footer_component", columnDefinition = "TEXT")
    private String footerComponent;

    @Column(name = "sidebar_component", columnDefinition = "TEXT")
    private String sidebarComponent;

    @Column(columnDefinition = "TEXT")
    private String css;

    @Column(columnDefinition = "TEXT")
    private String javascript;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Template createTemplate(String templateName, String templateHtml, String description, String templateType) {
        Template template = new Template();
        template.templateName = templateName;
        template.templateHtml = templateHtml;
        template.description = description;
        template.templateType = templateType;
        return template;
    }

    public void update(String templateName, String templateHtml, String description, String templateType) {
        this.templateName = templateName;
        this.templateHtml = templateHtml;
        this.description = description;
        this.templateType = templateType;
    }
} 