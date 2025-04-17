package egovframework.let.cms.user.service;

import egovframework.let.cms.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    
    User createUser(User user);
    
    User updateUser(User user);
    
    void deleteUser(String userId);
    
    Optional<User> getUserById(String userId);
    
    Optional<User> getUserByUsername(String username);
    
    Page<User> getUsers(Pageable pageable);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    User changePassword(String userId, String newPassword);
    
    User updateStatus(String userId, String status);
} 