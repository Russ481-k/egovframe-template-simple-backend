package egovframework.let.cms.content.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LET_CMS_CONTENT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 100)
    private String author;

    @Column(name = "content_type", length = 50)
    private String contentType;

    @Column(name = "is_published")
    private boolean isPublished;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

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

    @Column(name = "version_id")
    private Long versionId;

    @Column(name = "version_comment", length = 500)
    private String versionComment;

    @Column(name = "attached_files", columnDefinition = "TEXT")
    private String attachedFiles;

    @Column(name = "language_code", length = 10)
    private String languageCode;

    public static Content createContent(String title, String content, String author, String contentType) {
        Content contentEntity = new Content();
        contentEntity.title = title;
        contentEntity.content = content;
        contentEntity.author = author;
        contentEntity.contentType = contentType;
        contentEntity.isPublished = false;
        contentEntity.viewCount = 0;
        return contentEntity;
    }

    public void update(String title, String content, String author, String contentType) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.contentType = contentType;
    }

    public void publish() {
        this.isPublished = true;
        this.publishedAt = LocalDateTime.now();
    }

    public void unpublish() {
        this.isPublished = false;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void setVersion(Long versionId, String versionComment) {
        this.versionId = versionId;
        this.versionComment = versionComment;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setAttachedFiles(String attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
} 