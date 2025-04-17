package egovframework.let.cms.content.service.impl;

import egovframework.let.cms.content.domain.Content;
import egovframework.let.cms.content.exception.ContentNotFoundException;
import egovframework.let.cms.content.repository.ContentRepository;
import egovframework.let.cms.content.service.ContentService;
import egovframework.let.cms.content.service.dto.ContentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("contentService")
@RequiredArgsConstructor
@Transactional
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    public Long createContent(ContentDto contentDto) {
        Content content = Content.createContent(
            contentDto.getTitle(),
            contentDto.getContent(),
            contentDto.getAuthor(),
            contentDto.getContentType()
        );
        
        if (contentDto.getExpiredAt() != null) {
            content.setExpiredAt(contentDto.getExpiredAt());
        }
        
        if (contentDto.getAttachedFiles() != null) {
            content.setAttachedFiles(contentDto.getAttachedFiles());
        }
        
        if (contentDto.getLanguageCode() != null) {
            content.setLanguageCode(contentDto.getLanguageCode());
        }
        
        if (contentDto.isPublished()) {
            content.publish();
        }
        
        return contentRepository.save(content).getId();
    }

    @Override
    public void updateContent(Long contentId, ContentDto contentDto) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new ContentNotFoundException(contentId));
            
        content.update(
            contentDto.getTitle(),
            contentDto.getContent(),
            contentDto.getAuthor(),
            contentDto.getContentType()
        );
        
        if (contentDto.getExpiredAt() != null) {
            content.setExpiredAt(contentDto.getExpiredAt());
        }
        
        if (contentDto.getAttachedFiles() != null) {
            content.setAttachedFiles(contentDto.getAttachedFiles());
        }
        
        if (contentDto.getLanguageCode() != null) {
            content.setLanguageCode(contentDto.getLanguageCode());
        }
        
        if (contentDto.isPublished() && !content.isPublished()) {
            content.publish();
        } else if (!contentDto.isPublished() && content.isPublished()) {
            content.unpublish();
        }
    }

    @Override
    public void deleteContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new ContentNotFoundException(contentId));
        contentRepository.delete(content);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentDto getContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new ContentNotFoundException(contentId));
        return convertToDto(content);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDto> getContents(Pageable pageable) {
        return contentRepository.findPublishedContents(pageable)
            .map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDto> searchContents(String keyword, Pageable pageable) {
        return contentRepository.searchContents(keyword, pageable)
            .map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDto> getContentsByType(String contentType, Pageable pageable) {
        return contentRepository.findByContentType(contentType, pageable)
            .map(this::convertToDto);
    }

    @Override
    public void increaseViewCount(Long contentId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new ContentNotFoundException(contentId));
        content.increaseViewCount();
    }

    @Override
    public Long createVersion(Long contentId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new ContentNotFoundException(contentId));
            
        Content version = Content.createContent(
            content.getTitle(),
            content.getContent(),
            content.getAuthor(),
            content.getContentType()
        );
        
        version.setExpiredAt(content.getExpiredAt());
        version.setAttachedFiles(content.getAttachedFiles());
        version.setLanguageCode(content.getLanguageCode());
        version.setVersion(content.getId(), "Version created from content " + contentId);
        
        return contentRepository.save(version).getId();
    }

    @Override
    public void restoreVersion(Long contentId, Long versionId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new ContentNotFoundException(contentId));
            
        Content version = contentRepository.findById(versionId)
            .orElseThrow(() -> new ContentNotFoundException(versionId));
            
        content.update(
            version.getTitle(),
            version.getContent(),
            version.getAuthor(),
            version.getContentType()
        );
        
        content.setExpiredAt(version.getExpiredAt());
        content.setAttachedFiles(version.getAttachedFiles());
        content.setLanguageCode(version.getLanguageCode());
    }

    private ContentDto convertToDto(Content content) {
        ContentDto dto = new ContentDto();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setContent(content.getContent());
        dto.setAuthor(content.getAuthor());
        dto.setContentType(content.getContentType());
        dto.setPublished(content.isPublished());
        dto.setViewCount(content.getViewCount());
        dto.setPublishedAt(content.getPublishedAt());
        dto.setExpiredAt(content.getExpiredAt());
        dto.setCreatedBy(content.getCreatedBy());
        dto.setUpdatedBy(content.getUpdatedBy());
        dto.setCreatedAt(content.getCreatedAt());
        dto.setUpdatedAt(content.getUpdatedAt());
        dto.setVersionId(content.getVersionId());
        dto.setVersionComment(content.getVersionComment());
        dto.setAttachedFiles(content.getAttachedFiles());
        dto.setLanguageCode(content.getLanguageCode());
        return dto;
    }
} 