package egovframework.let.cms.user.service.impl;

import egovframework.let.cms.user.domain.Cms05Role;
import egovframework.let.cms.user.domain.Cms05User;
import egovframework.let.cms.user.dto.SiteInfo;
import egovframework.let.cms.user.dto.SiteManagerRegisterRequest;
import egovframework.let.cms.user.dto.UserDto;
import egovframework.let.cms.user.dto.UserRegisterRequest;
import egovframework.let.cms.user.repository.RoleRepository;
import egovframework.let.cms.user.repository.UserRepository;
import egovframework.let.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        Cms05User user = new Cms05User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setStatus(userDto.getStatus());
        
        Cms05Role role = roleRepository.findByRoleName(userDto.getRole().getRoleName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role type"));
        user.setRole(role);
        
        Cms05User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        Cms05User user = userRepository.findById(Long.parseLong(userDto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setStatus(userDto.getStatus());
        
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        Cms05User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteById(Long.parseLong(userId));
    }

    @Override
    public Optional<UserDto> getUserById(String userId) {
        return userRepository.findById(Long.parseLong(userId))
                .map(this::convertToDto);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public void changePassword(String userId, String newPassword) {
        Cms05User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto updateStatus(String userId, String status) {
        Cms05User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setStatus(status);
        return convertToDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public Cms05User registerUser(UserRegisterRequest request) {
        Cms05Role role = roleRepository.findByRoleName(request.getRoleType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role type"));

        Cms05User user = new Cms05User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Cms05User registerSiteManager(SiteManagerRegisterRequest request) {
        Cms05Role role = roleRepository.findByRoleName("SITE_MANAGER")
                .orElseThrow(() -> new IllegalArgumentException("SITE_MANAGER role not found"));

        Cms05User user = new Cms05User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(role);
        user.setSiteName(request.getSiteName());
        user.setSiteDescription(request.getSiteDescription());
        user.setSiteUrl(request.getSiteUrl());

        return userRepository.save(user);
    }

    @Override
    public SiteInfo getSiteInfo() {
        List<Cms05User> siteManagers = userRepository.findByRole_RoleName("SITE_MANAGER");
        if (siteManagers.isEmpty()) {
            throw new IllegalArgumentException("No site manager found");
        }

        Cms05User siteManager = siteManagers.get(0);
        SiteInfo siteInfo = new SiteInfo();
        siteInfo.setSiteName(siteManager.getSiteName());
        siteInfo.setSiteDescription(siteManager.getSiteDescription());
        siteInfo.setSiteUrl(siteManager.getSiteUrl());
        siteInfo.setLastUpdated(siteManager.getUpdatedAt() != null ? siteManager.getUpdatedAt() : siteManager.getCreatedAt());

        return siteInfo;
    }

    private UserDto convertToDto(Cms05User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId().toString());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());
        return dto;
    }
} 