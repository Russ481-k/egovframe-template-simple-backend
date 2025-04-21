package egovframework.com.cmm.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;

    public static class ApiResponseBuilder<T> {
        private String status;
        private int code;
        private String message;
        private T data;

        public ApiResponseBuilder<T> status(String status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(status, code, message, data);
        }
    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(0)
                .message("정상 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .status("error")
                .code(code)
                .message(message)
                .data(null)
                .build();
    }

    public static ApiResponse<Void> successVoid(String message) {
        return ApiResponse.<Void>builder()
                .status("success")
                .code(0)
                .message(message)
                .data(null)
                .build();
    }
} 