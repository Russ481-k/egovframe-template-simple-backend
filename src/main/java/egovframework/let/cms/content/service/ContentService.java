package egovframework.let.cms.content.service;

import egovframework.let.cms.content.service.dto.ContentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {
    
    /**
     * 컨텐츠를 등록한다.
     * @param contentDto 컨텐츠 정보
     * @return 등록된 컨텐츠 ID
     */
    Long createContent(ContentDto contentDto);
    
    /**
     * 컨텐츠를 수정한다.
     * @param contentId 컨텐츠 ID
     * @param contentDto 컨텐츠 정보
     */
    void updateContent(Long contentId, ContentDto contentDto);
    
    /**
     * 컨텐츠를 삭제한다.
     * @param contentId 컨텐츠 ID
     */
    void deleteContent(Long contentId);
    
    /**
     * 컨텐츠를 조회한다.
     * @param contentId 컨텐츠 ID
     * @return 컨텐츠 정보
     */
    ContentDto getContent(Long contentId);
    
    /**
     * 컨텐츠 목록을 조회한다.
     * @param pageable 페이지 정보
     * @return 컨텐츠 목록
     */
    Page<ContentDto> getContents(Pageable pageable);
    
    /**
     * 키워드로 컨텐츠를 검색한다.
     * @param keyword 검색 키워드
     * @param pageable 페이지 정보
     * @return 검색된 컨텐츠 목록
     */
    Page<ContentDto> searchContents(String keyword, Pageable pageable);
    
    /**
     * 컨텐츠 타입별 목록을 조회한다.
     * @param contentType 컨텐츠 타입
     * @param pageable 페이지 정보
     * @return 컨텐츠 목록
     */
    Page<ContentDto> getContentsByType(String contentType, Pageable pageable);
    
    /**
     * 컨텐츠 조회수를 증가시킨다.
     * @param contentId 컨텐츠 ID
     */
    void increaseViewCount(Long contentId);
    
    /**
     * 컨텐츠 버전을 생성한다.
     * @param contentId 컨텐츠 ID
     * @return 생성된 버전 ID
     */
    Long createVersion(Long contentId);
    
    /**
     * 컨텐츠 버전을 복원한다.
     * @param contentId 컨텐츠 ID
     * @param versionId 버전 ID
     */
    void restoreVersion(Long contentId, Long versionId);
} 