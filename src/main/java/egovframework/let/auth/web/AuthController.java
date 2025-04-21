package egovframework.let.auth.web;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "cmm_00_Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Operation(summary = "일반 로그인", description = "일반 로그인 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "300", description = "로그인 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginVO loginVO) {
        return ResponseEntity.ok(authService.login(loginVO));
    }

    @Operation(summary = "SNS 로그인", description = "SNS 로그인 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "300", description = "로그인 실패")
    })
    @PostMapping("/sns/{provider}")
    public ResponseEntity<Map<String, Object>> snsLogin(
            @PathVariable String provider,
            @RequestBody Map<String, String> token) {
        return ResponseEntity.ok(authService.snsLogin(provider, token.get("token")));
    }

    @Operation(summary = "로그아웃", description = "로그아웃 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    @PostMapping("/logout")
    public ResponseEntity<ResultVO> logout(HttpServletRequest request) {
        return ResponseEntity.ok(authService.logout(request));
    }

    @Operation(summary = "토큰 검증", description = "JWT 토큰 유효성 검증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유효한 토큰"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    @PostMapping("/validate")
    public ResponseEntity<ResultVO> validateToken(HttpServletRequest request) {
        return ResponseEntity.ok(authService.validateToken(request));
    }

    @Operation(summary = "비밀번호 변경", description = "사용자 비밀번호 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @SecurityRequirement(name = "Authorization")
    @PatchMapping("/password")
    public ResponseEntity<ResultVO> changePassword(
            @RequestBody Map<String, String> passwordMap,
            @Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) {
        return ResponseEntity.ok(authService.changePassword(passwordMap, user));
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            String token = refreshToken.substring(7); // "Bearer " 제거
            if (!jwtTokenProvider.validateToken(token)) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "유효하지 않은 리프레시 토큰입니다.");
                return ResponseEntity.badRequest().body(error);
            }

            String username = jwtTokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            String newAccessToken = jwtTokenProvider.createToken(authentication);
            String newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", newRefreshToken);

            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "토큰 갱신에 실패했습니다.");
            return ResponseEntity.badRequest().body(error);
        }
    }
} 
 
 
 