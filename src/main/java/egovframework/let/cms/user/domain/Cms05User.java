package egovframework.let.cms.user.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CMS05_USER")
@Getter
@Setter
public class Cms05User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;
    private String phone;
    private String status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Cms05Role role;

    // 사이트 관리자 관련 필드
    private String siteName;
    private String siteDescription;
    private String siteUrl;

    // 비밀번호 재설정 관련 필드
    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 