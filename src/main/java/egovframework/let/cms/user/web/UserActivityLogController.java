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

@Tag(name = "UserActivityLog", description = "사용자 활동 로그 API")
@RestController
@RequestMapping("/api/user-activity-logs")
@RequiredArgsConstructor
public class UserActivityLogController {

    private final UserActivityLogService userActivityLogService;

    @Operation(summary = "Get user activities", description = "Retrieve activity logs for a specific user")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Activity logs retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.username")
    public ResponseEntity<ApiResponse<List<UserActivityLog>>> getUserActivities(
            @Parameter(description = "User ID", required = true)
            @PathVariable String userId) {
        List<UserActivityLog> activities = userActivityLogService.getUserActivities(userId);
        return ResponseEntity.ok(ApiResponse.success("Activity logs retrieved successfully", activities));
    }

    @Operation(summary = "Get user activities by date range", description = "Retrieve activity logs for a specific user within a date range")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Activity logs retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{userId}/date-range")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.username")
    public ResponseEntity<ApiResponse<List<UserActivityLog>>> getUserActivitiesByDateRange(
            @Parameter(description = "User ID", required = true)
            @PathVariable String userId,
            @Parameter(description = "Start date", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<UserActivityLog> activities = userActivityLogService.getUserActivitiesByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Activity logs retrieved successfully", activities));
    }
} 