package egovframework.let.cms.template.exception;

public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException(Long templateId) {
        super("Template not found with id: " + templateId);
    }
} 