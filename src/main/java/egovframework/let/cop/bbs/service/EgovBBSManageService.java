package egovframework.let.cop.bbs.service;

import java.util.Map;

import egovframework.let.cop.bbs.domain.BoardVO;

/**
 * 게시물 관리를 위한 서비스 인터페이스  클래스
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009.03.19
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.19  이삼섭          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성 
 *  
 *  </pre>
 */
public interface EgovBBSManageService {

	/**
	 * 게시물 한 건을 삭제 한다.
	 * 
	 * @param board
	 * @exception Exception Exception
	 */
	void deleteBoardArticle(BoardVO board) throws Exception;

	/**
	 * 방명록 내용을 삭제 한다.
	 * 
	 * @param boardVO
	 * @exception Exception Exception
	 */
	void deleteGuestList(BoardVO boardVO) throws Exception;

	/**
	 * 방명록에 대한 패스워드를 조회 한다.
	 * @return
	 * 
	 * @param board
	 * @exception Exception Exception
	 */
	String getPasswordInf(BoardVO board) throws Exception;

	/**
	 * 게시판에 게시물 또는 답변 게시물을 등록 한다.
	 * 
	 * @param board
	 * @exception Exception Exception
	 */
	void insertBoardArticle(BoardVO board) throws Exception;

	/**
	 * 게시물 대하여 상세 내용을 조회 한다.
	 * @return
	 * 
	 * @param boardVO
	 * @exception Exception Exception
	 */
	BoardVO selectBoardArticle(BoardVO boardVO) throws Exception;

	/**
	 * 조건에 맞는 게시물 목록을 조회 한다.
	 * @return
	 * 
	 * @param boardVO
	 * @param attrbFlag
	 * @exception Exception Exception
	 */
	Map<String, Object> selectBoardArticles(BoardVO boardVO, String attrbFlag) throws Exception;

	/**
	 * 방명록에 대한 목록을 조회 한다.
	 * @return
	 * 
	 * @param boardVO
	 * @exception Exception Exception
	 */
	Map<String, Object> selectGuestList(BoardVO boardVO) throws Exception;

	/**
	 * 게시물 한 건의 내용을 수정 한다.
	 * 
	 * @param board
	 * @exception Exception Exception
	 */
	void updateBoardArticle(BoardVO board) throws Exception;

}