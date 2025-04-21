package egovframework.let.cms.user.repository;

import egovframework.let.cms.user.domain.Cms05Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Cms05Role, Long> {
    Optional<Cms05Role> findByRoleName(String roleName);
} 