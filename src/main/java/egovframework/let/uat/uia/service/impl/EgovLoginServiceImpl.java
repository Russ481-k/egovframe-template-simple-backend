package egovframework.let.uat.uia.service.impl;

import egovframework.com.cmm.LoginVO;
import egovframework.com.jwt.service.JwtTokenProvider;
import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.let.utl.sim.service.EgovFileScrty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 일반 로그인을 처리하는 비즈니스 구현 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *  </pre>
 */
@Slf4j
@Service("loginService")
@RequiredArgsConstructor
public class EgovLoginServiceImpl implements EgovLoginService {

	private final LoginDAO loginDAO;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	@Override
	public Map<String, Object> actionLogin(LoginVO vo) throws Exception {
		Map<String, Object> result = new HashMap<>();

		// 1. 입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getId());
		vo.setPassword(enpassword);

		// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
		LoginVO loginVO = loginDAO.actionLogin(vo);

		if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
			// 3. Spring Security 인증 처리
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(vo.getId(), vo.getPassword())
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// 4. JWT 토큰 생성
			String token = jwtTokenProvider.createToken(authentication);
			String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
			
			result.put("token", token);
			result.put("refreshToken", refreshToken);
			result.put("loginVO", loginVO);
			result.put("status", "success");
		} else {
			result.put("status", "fail");
			result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		return result;
	}

	/**
	 * 아이디를 찾는다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	@Override
	public LoginVO searchId(LoginVO vo) throws Exception {
		return loginDAO.searchId(vo);
	}

	/**
	 * 비밀번호를 찾는다.
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean searchPassword(LoginVO vo) throws Exception {
		// 1. 입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getId());
		vo.setPassword(enpassword);

		// 2. 변경된 비밀번호를 저장한다.
		try {
			loginDAO.updatePassword(vo);
			return true;
		} catch (Exception e) {
			log.error("Password update failed", e);
			return false;
		}
	}
}