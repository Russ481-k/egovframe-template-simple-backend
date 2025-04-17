package egovframework.let.cms.common.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
    @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
    @ApiResponse(responseCode = "403", description = "접근이 거부되었습니다."),
    @ApiResponse(responseCode = "404", description = "요청한 리소스를 찾을 수 없습니다."),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.")
})
public @interface DefaultApiResponses {
} 