package egovframework.let.cms.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "API 응답")
public class ApiResponseSchema<T> {
    @Schema(description = "성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    public ApiResponseSchema(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseSchema<T> success(T data) {
        return new ApiResponseSchema<>(true, "요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponseSchema<T> success(String message, T data) {
        return new ApiResponseSchema<>(true, message, data);
    }

    public static <T> ApiResponseSchema<T> error(String message) {
        return new ApiResponseSchema<>(false, message, null);
    }

    public static <T> ApiResponseSchema<T> error(String message, T data) {
        return new ApiResponseSchema<>(false, message, data);
    }
} 