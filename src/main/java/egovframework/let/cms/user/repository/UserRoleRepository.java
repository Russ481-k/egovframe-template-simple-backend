package egovframework.let.cms.user.repository;

import egovframework.let.cms.user.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    
    Optional<UserRole> findByRoleName(String roleName);
} 