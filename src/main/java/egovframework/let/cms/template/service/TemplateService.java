package egovframework.let.cms.template.service;

import egovframework.let.cms.template.service.dto.TemplateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemplateService {
    
    /**
     * 템플릿을 등록한다.
     * @param templateDto 템플릿 정보
     * @return 등록된 템플릿 ID
     */
    Long createTemplate(TemplateDto templateDto);
    
    /**
     * 템플릿을 수정한다.
     * @param templateId 템플릿 ID
     * @param templateDto 템플릿 정보
     */
    void updateTemplate(Long templateId, TemplateDto templateDto);
    
    /**
     * 템플릿을 삭제한다.
     * @param templateId 템플릿 ID
     */
    void deleteTemplate(Long templateId);
    
    /**
     * 템플릿을 조회한다.
     * @param templateId 템플릿 ID
     * @return 템플릿 정보
     */
    TemplateDto getTemplate(Long templateId);
    
    /**
     * 템플릿 목록을 조회한다.
     * @param pageable 페이지 정보
     * @return 템플릿 목록
     */
    Page<TemplateDto> getTemplates(Pageable pageable);
    
    /**
     * 템플릿 미리보기를 생성한다.
     * @param templateId 템플릿 ID
     * @return 미리보기 HTML
     */
    String generatePreview(Long templateId);
    
    /**
     * 템플릿을 적용한다.
     * @param templateId 템플릿 ID
     * @param targetId 적용 대상 ID (메뉴, 컨텐츠 등)
     * @param targetType 적용 대상 타입 (MENU, CONTENT 등)
     */
    void applyTemplate(Long templateId, Long targetId, String targetType);
} 