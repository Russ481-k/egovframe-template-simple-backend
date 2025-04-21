package egovframework.com.cmm;

import lombok.Getter;

@Getter
public enum ResponseCode {
	SUCCESS("success", 0, "정상 처리되었습니다."),
	UNAUTHORIZED("error", 401, "인증이 필요합니다."),
	FORBIDDEN("error", 403, "접근 권한이 없습니다."),
	NOT_FOUND("error", 404, "요청한 리소스를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR("error", 500, "서버 내부 오류가 발생했습니다."),
	SAVE_ERROR("error", 900, "입력값 무결성 오류"),
	INPUT_CHECK_ERROR("error", 901, "입력값 검증 오류");

	private final String status;
	private final int code;
	private final String message;

	ResponseCode(String status, int code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}






