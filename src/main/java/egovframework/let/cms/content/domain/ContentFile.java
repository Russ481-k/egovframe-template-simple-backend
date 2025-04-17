package egovframework.let.cms.content.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LET_CMS_CONTENT_FILE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(nullable = false, length = 255)
    private String originalFileName;

    @Column(nullable = false, length = 255)
    private String storedFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize;

    @Column(length = 100)
    private String fileType;

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

    public static ContentFile createContentFile(
            Content content,
            String originalFileName,
            String storedFileName,
            String filePath,
            Long fileSize,
            String fileType) {
        ContentFile contentFile = new ContentFile();
        contentFile.content = content;
        contentFile.originalFileName = originalFileName;
        contentFile.storedFileName = storedFileName;
        contentFile.filePath = filePath;
        contentFile.fileSize = fileSize;
        contentFile.fileType = fileType;
        return contentFile;
    }
} 