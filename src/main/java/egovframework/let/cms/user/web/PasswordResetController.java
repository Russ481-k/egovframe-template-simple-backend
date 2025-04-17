package egovframework.let.cms.user.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.user.dto.PasswordResetConfirmRequest;
import egovframework.let.cms.user.dto.PasswordResetRequest;
import egovframework.let.cms.user.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserPasswordReset", description = "비밀번호 재설정 API")
@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Operation(
        summary = "비밀번호 재설정 요청",
        description = "이메일로 비밀번호 재설정 링크를 전송합니다. 링크는 24시간 동안만 유효합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "재설정 링크 전송 성공",
            content = @Content(
                examples = @ExampleObject(
                    value = "{\"success\": true, \"message\": \"비밀번호 재설정 링크가 이메일로 전송되었습니다.\"}"
                )
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 요청",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Invalid Email",
                        value = "{\"success\": false, \"message\": \"해당 이메일로 등록된 사용자가 없습니다.\"}"
                    ),
                    @ExampleObject(
                        name = "Inactive User",
                        value = "{\"success\": false, \"message\": \"비활성화된 계정입니다.\"}"
                    )
                }
            )
        )
    })
    @PostMapping("/request")
    public ResponseEntity<ApiResponse<String>> requestPasswordReset(
            @Parameter(description = "비밀번호 재설정 요청 정보", required = true)
            @Valid @RequestBody PasswordResetRequest request) {
        passwordResetService.requestPasswordReset(request);
        return ResponseEntity.ok(ApiResponse.success("비밀번호 재설정 링크가 이메일로 전송되었습니다."));
    }

    @Operation(
        summary = "비밀번호 재설정",
        description = "재설정 토큰을 사용하여 비밀번호를 변경합니다. 토큰은 이메일로 전송된 링크에서 확인할 수 있습니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "비밀번호 변경 성공",
            content = @Content(
                examples = @ExampleObject(
                    value = "{\"success\": true, \"message\": \"비밀번호가 성공적으로 변경되었습니다.\"}"
                )
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 요청",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Invalid Token",
                        value = "{\"success\": false, \"message\": \"유효하지 않은 토큰입니다.\"}"
                    ),
                    @ExampleObject(
                        name = "Expired Token",
                        value = "{\"success\": false, \"message\": \"만료된 토큰입니다.\"}"
                    ),
                    @ExampleObject(
                        name = "Inactive User",
                        value = "{\"success\": false, \"message\": \"비활성화된 계정입니다.\"}"
                    )
                }
            )
        )
    })
    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @Parameter(description = "비밀번호 재설정 확인 정보", required = true)
            @Valid @RequestBody PasswordResetConfirmRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("비밀번호가 성공적으로 변경되었습니다."));
    }
} 