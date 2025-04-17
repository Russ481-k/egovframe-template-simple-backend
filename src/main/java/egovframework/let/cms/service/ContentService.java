package egovframework.let.cms.service;

import egovframework.let.cms.content.domain.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {
    Content createContent(Content content);
    Content updateContent(Long id, Content content);
    void deleteContent(Long id);
    Content getContent(Long id);
    Page<Content> getContents(Pageable pageable);
    Page<Content> searchContents(String keyword, Pageable pageable);
    Page<Content> getContentsByType(String contentType, Pageable pageable);
    void increaseViewCount(Long id);
} 