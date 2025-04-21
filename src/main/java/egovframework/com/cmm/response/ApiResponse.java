package egovframework.com.cmm.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(0)
                .message("성공")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(0)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .status("error")
                .code(1)
                .message(message)
                .data(null)
                .build();
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
 
 
 