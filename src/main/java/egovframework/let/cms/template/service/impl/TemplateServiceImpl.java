package egovframework.let.cms.template.service.impl;

import egovframework.let.cms.template.domain.Template;
import egovframework.let.cms.template.exception.TemplateNotFoundException;
import egovframework.let.cms.template.repository.TemplateRepository;
import egovframework.let.cms.template.service.TemplateService;
import egovframework.let.cms.template.service.dto.TemplateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("templateService")
@RequiredArgsConstructor
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public Long createTemplate(TemplateDto templateDto) {
        Template template = Template.createTemplate(
            templateDto.getTemplateName(),
            templateDto.getTemplateHtml(),
            templateDto.getDescription(),
            templateDto.getTemplateType()
        );
        
        if (templateDto.getDeviceType() != null) {
            template.setDeviceType(templateDto.getDeviceType());
        }
        
        if (templateDto.getHeaderComponent() != null) {
            template.setHeaderComponent(templateDto.getHeaderComponent());
        }
        
        if (templateDto.getFooterComponent() != null) {
            template.setFooterComponent(templateDto.getFooterComponent());
        }
        
        if (templateDto.getSidebarComponent() != null) {
            template.setSidebarComponent(templateDto.getSidebarComponent());
        }
        
        if (templateDto.getCss() != null) {
            template.setCss(templateDto.getCss());
        }
        
        if (templateDto.getJavascript() != null) {
            template.setJavascript(templateDto.getJavascript());
        }
        
        return templateRepository.save(template).getId();
    }

    @Override
    public void updateTemplate(Long templateId, TemplateDto templateDto) {
        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(templateId));
            
        template.update(
            templateDto.getTemplateName(),
            templateDto.getTemplateHtml(),
            templateDto.getDescription(),
            templateDto.getTemplateType()
        );
        
        if (templateDto.getDeviceType() != null) {
            template.setDeviceType(templateDto.getDeviceType());
        }
        
        if (templateDto.getHeaderComponent() != null) {
            template.setHeaderComponent(templateDto.getHeaderComponent());
        }
        
        if (templateDto.getFooterComponent() != null) {
            template.setFooterComponent(templateDto.getFooterComponent());
        }
        
        if (templateDto.getSidebarComponent() != null) {
            template.setSidebarComponent(templateDto.getSidebarComponent());
        }
        
        if (templateDto.getCss() != null) {
            template.setCss(templateDto.getCss());
        }
        
        if (templateDto.getJavascript() != null) {
            template.setJavascript(templateDto.getJavascript());
        }
    }

    @Override
    public void deleteTemplate(Long templateId) {
        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(templateId));
        templateRepository.delete(template);
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateDto getTemplate(Long templateId) {
        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(templateId));
        return convertToDto(template);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateDto> getTemplates(Pageable pageable) {
        return templateRepository.findAll(pageable)
            .map(this::convertToDto);
    }

    @Override
    public String generatePreview(Long templateId) {
        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(templateId));
            
        // TODO: 실제 미리보기 생성 로직 구현
        return template.getTemplateHtml();
    }

    @Override
    public void applyTemplate(Long templateId, Long targetId, String targetType) {
        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(templateId));
            
        // TODO: 실제 템플릿 적용 로직 구현
        // targetType에 따라 다른 서비스를 호출하여 템플릿을 적용
    }

    private TemplateDto convertToDto(Template template) {
        TemplateDto dto = new TemplateDto();
        dto.setId(template.getId());
        dto.setTemplateName(template.getTemplateName());
        dto.setTemplateHtml(template.getTemplateHtml());
        dto.setDescription(template.getDescription());
        dto.setTemplateType(template.getTemplateType());
        dto.setDeviceType(template.getDeviceType());
        dto.setHeaderComponent(template.getHeaderComponent());
        dto.setFooterComponent(template.getFooterComponent());
        dto.setSidebarComponent(template.getSidebarComponent());
        dto.setCss(template.getCss());
        dto.setJavascript(template.getJavascript());
        return dto;
    }
} 