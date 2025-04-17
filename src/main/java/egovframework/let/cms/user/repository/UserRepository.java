package egovframework.let.cms.user.repository;

import egovframework.let.cms.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
} 