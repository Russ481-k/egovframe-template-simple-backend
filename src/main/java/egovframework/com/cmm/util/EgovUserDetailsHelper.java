package egovframework.com.cmm.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import egovframework.com.cmm.LoginVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EgovUserDetails Helper 클래스
 *
 * @author sjyoon
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2009.03.10  sjyoon    최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *   2024.03.14  JWT 기반 인증으로 변경
 *
 * </pre>
 */
public class EgovUserDetailsHelper {

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static Object getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof LoginVO) {
			return authentication.getPrincipal();
		}
		return null;
	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 * @return List<String> - 사용자 권한정보 목록
	 */
	public static List<String> getAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && 
			   authentication.getPrincipal() instanceof LoginVO && 
			   !"anonymousUser".equals(authentication.getPrincipal());
	}
}
