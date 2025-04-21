package egovframework.let.auth.service.impl;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.service.impl.EgovUserDetailsService;
import egovframework.let.auth.service.AuthService;
import egovframework.com.jwt.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Map<String, Object> login(LoginVO loginVO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginVO.getUserSe() + loginVO.getId(), loginVO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("user", authentication.getPrincipal());
        return response;
    }

    @Override
    public Map<String, Object> snsLogin(String provider, String token) {
        // TODO: Implement SNS login logic
        throw new UnsupportedOperationException("SNS login not implemented yet");
    }

    @Override
    public ResultVO logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(200);
        resultVO.setResultMessage("로그아웃 되었습니다.");
        return resultVO;
    }

    @Override
    public ResultVO validateToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            ResultVO resultVO = new ResultVO();
            resultVO.setResultCode(200);
            resultVO.setResultMessage("유효한 토큰입니다.");
            return resultVO;
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(401);
        resultVO.setResultMessage("유효하지 않은 토큰입니다.");
        return resultVO;
    }

    @Override
    public ResultVO changePassword(Map<String, String> passwordMap, LoginVO user) {
        // TODO: Implement password change logic
        throw new UnsupportedOperationException("Password change not implemented yet");
    }
} 