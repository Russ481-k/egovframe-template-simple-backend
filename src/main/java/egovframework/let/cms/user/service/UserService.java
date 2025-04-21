package egovframework.let.cms.user.service;

import egovframework.let.cms.user.domain.Cms05User;
import egovframework.let.cms.user.dto.SiteInfo;
import egovframework.let.cms.user.dto.SiteManagerRegisterRequest;
import egovframework.let.cms.user.dto.UserDto;
import egovframework.let.cms.user.dto.UserRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(String userId);
    Optional<UserDto> getUserById(String userId);
    Page<UserDto> getUsers(Pageable pageable);
    void changePassword(String userId, String newPassword);
    UserDto updateStatus(String userId, String status);
    Cms05User registerUser(UserRegisterRequest request);
    Cms05User registerSiteManager(SiteManagerRegisterRequest request);
    SiteInfo getSiteInfo();
} 