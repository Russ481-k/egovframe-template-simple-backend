package egovframework.let.cms.content.repository;

import egovframework.let.cms.content.domain.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT c FROM Content c WHERE " +
           "(:keyword IS NULL OR " +
           "c.title LIKE %:keyword% OR " +
           "c.content LIKE %:keyword% OR " +
           "c.author LIKE %:keyword%)")
    Page<Content> searchContents(@Param("keyword") String keyword, Pageable pageable);

    Page<Content> findByContentType(String contentType, Pageable pageable);

    Page<Content> findByIsPublishedTrue(Pageable pageable);

    @Query("SELECT c FROM Content c WHERE c.isPublished = true AND " +
           "(c.expiredAt IS NULL OR c.expiredAt > CURRENT_TIMESTAMP)")
    Page<Content> findPublishedContents(Pageable pageable);
} 