package egovframework.let.cms.user.web;

import egovframework.let.cms.user.domain.UserActivityLog;
import egovframework.let.cms.user.service.UserActivityLogService;
import egovframework.let.cms.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "cms_05_User", description = "사용자 활동 로그 API")
@RestController
@RequestMapping("/api/v1/users/{userId}/activity-logs")
@RequiredArgsConstructor
public class UserActivityLogController {

    private final UserActivityLogService userActivityLogService;

    @Operation(summary = "사용자 활동 로그 조회", description = "특정 사용자의 활동 로그를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "활동 로그 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한이 없는 사용자")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.username")
    public ResponseEntity<ApiResponse<List<UserActivityLog>>> getUserActivities(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable String userId) {
        List<UserActivityLog> activities = userActivityLogService.getUserActivities(userId);
        return ResponseEntity.ok(ApiResponse.success(activities));
    }

    @Operation(summary = "기간별 사용자 활동 로그 조회", description = "특정 기간 동안의 사용자 활동 로그를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "활동 로그 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한이 없는 사용자")
    })
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.username")
    public ResponseEntity<ApiResponse<List<UserActivityLog>>> getUserActivitiesByDateRange(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable String userId,
            @Parameter(description = "시작일시", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "종료일시", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<UserActivityLog> activities = userActivityLogService.getUserActivitiesByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(activities));
    }
} 