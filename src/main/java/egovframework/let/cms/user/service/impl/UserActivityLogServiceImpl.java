package egovframework.let.cms.user.service.impl;

import egovframework.let.cms.user.domain.UserActivityLog;
import egovframework.let.cms.user.repository.UserActivityLogRepository;
import egovframework.let.cms.user.service.UserActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityLogServiceImpl implements UserActivityLogService {

    private final UserActivityLogRepository userActivityLogRepository;

    @Override
    @Transactional
    public void logActivity(String userId, String action, String description, String ipAddress, String userAgent) {
        UserActivityLog log = new UserActivityLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setDescription(description);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);

        userActivityLogRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserActivityLog> getUserActivities(String userId) {
        return userActivityLogRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserActivityLog> getUserActivitiesByDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return userActivityLogRepository.findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(userId, startDate, endDate);
    }
} 