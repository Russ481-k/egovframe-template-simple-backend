package egovframework.let.auth.service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.ResultVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthService {
    Map<String, Object> login(LoginVO loginVO);
    Map<String, Object> snsLogin(String provider, String token);
    ResultVO logout(HttpServletRequest request);
    ResultVO validateToken(HttpServletRequest request);
    ResultVO changePassword(Map<String, String> passwordMap, LoginVO user);
} 
 
 
 