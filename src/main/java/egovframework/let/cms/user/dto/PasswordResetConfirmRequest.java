package egovframework.let.cms.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "비밀번호 재설정 확인 DTO")
public class PasswordResetConfirmRequest {
    @Schema(description = "재설정 토큰", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotBlank(message = "토큰은 필수 입력값입니다.")
    private String token;

    @Schema(description = "새 비밀번호", example = "newPassword123!", required = true)
    @NotBlank(message = "새 비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String newPassword;
} 