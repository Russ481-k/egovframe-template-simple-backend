package egovframework.let.cms.user.service;

import egovframework.let.cms.user.domain.UserActivityLog;
import java.time.LocalDateTime;
import java.util.List;

public interface UserActivityLogService {
    /**
     * 사용자 활동 로그를 기록합니다.
     * @param userId 사용자 ID
     * @param action 활동 유형
     * @param description 활동 설명
     * @param ipAddress IP 주소
     * @param userAgent 사용자 에이전트
     */
    void logActivity(String userId, String action, String description, String ipAddress, String userAgent);

    /**
     * 특정 사용자의 활동 로그를 조회합니다.
     * @param userId 사용자 ID
     * @return 활동 로그 목록
     */
    List<UserActivityLog> getUserActivities(String userId);

    /**
     * 특정 기간 동안의 사용자 활동 로그를 조회합니다.
     * @param userId 사용자 ID
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 활동 로그 목록
     */
    List<UserActivityLog> getUserActivitiesByDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);
} 