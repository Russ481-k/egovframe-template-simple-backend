package egovframework.let.cms.user.repository;

import egovframework.let.cms.user.domain.Cms05User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Cms05User, Long> {
    Optional<Cms05User> findByUsername(String username);
    Optional<Cms05User> findByEmail(String email);
    Optional<Cms05User> findByResetToken(String resetToken);
    List<Cms05User> findByRole_RoleName(String roleName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 