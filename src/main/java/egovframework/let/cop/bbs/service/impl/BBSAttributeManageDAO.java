package egovframework.let.cop.bbs.service.impl;

import java.util.List;

import egovframework.let.cop.bbs.domain.BoardMasterVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 게시판 속성정보 관리를 위한 데이터 접근 클래스
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009.03.12
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.12  이삼섭          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *  </pre>
 */
@Repository("BBSAttributeManageDAO")
@Mapper
public interface BBSAttributeManageDAO {

    /**
     * 등록된 게시판 속성정보를 삭제한다.
     *
     * @param BoardMaster
     */
    void deleteBBSMasterInf(BoardMasterVO boardMaster) throws Exception;

    /**
     * 신규 게시판 속성정보를 등록한다.
     *
     * @param BoardMaster
     */
    void insertBBSMasterInf(BoardMasterVO boardMaster) throws Exception;

    /**
     * 게시판 속성정보 한 건을 상세조회 한다.
     *
     * @param BoardMasterVO
     */
    BoardMasterVO selectBBSMasterInf(BoardMasterVO searchVO) throws Exception;

    /**
     * 게시판 속성정보 목록을 조회한다.
     *
     * @param BoardMasterVO
     */
    List<BoardMasterVO> selectBBSMasterInfs(BoardMasterVO searchVO) throws Exception;

    /**
     * 게시판 속성정보 목록 숫자를 조회한다
     *
     * @param vo
     * @return
     * @throws Exception
     */
    int selectBBSMasterInfsCnt(BoardMasterVO searchVO) throws Exception;

    /**
     * 게시판 속성정보를 수정한다.
     *
     * @param BoardMaster
     */
    void updateBBSMasterInf(BoardMasterVO boardMaster) throws Exception;

    /**
     * 템플릿의 유효여부를 점검한다.
     *
     * @param BoardMasterVO
     */
    boolean validateTemplate(BoardMasterVO vo) throws Exception;

    /**
     * 유효한 게시판 목록을 불러온다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    List<BoardMasterVO> selectAllBBSMasteInf(BoardMasterVO vo) throws Exception;

    /**
     * 사용중인 게시판 속성정보 목록을 조회한다.
     *
     * @param BoardMasterVO
     */
    List<BoardMasterVO> selectBdMstrListByTrget(BoardMasterVO vo) throws Exception;

    /**
     * 사용중인 게시판 속성정보 목록 숫자를 조회한다
     *
     * @param vo
     * @return
     * @throws Exception
     */
    int selectBdMstrListCntByTrget(BoardMasterVO vo) throws Exception;

    /**
     * 커뮤니티, 동호회등 게시판 사용등록이 된 게시판 목록 전체를 불러온다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    List<BoardMasterVO> selectAllBdMstrByTrget(BoardMasterVO vo) throws Exception;

    /**
     * 사용 중이지 않은 게시판 속성정보 목록을 조회한다.
     *
     * @param BoardMasterVO
     */
    List<BoardMasterVO> selectNotUsedBdMstrList(BoardMasterVO vo) throws Exception;

    /**
     * 사용 중이지 않은 게시판 속성정보 목록 숫자를 조회한다
     *
     * @param vo
     * @return
     * @throws Exception
     */
    int selectNotUsedBdMstrListCnt(BoardMasterVO vo) throws Exception;
}
