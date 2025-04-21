package egovframework.let.cop.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.let.cop.bbs.domain.BoardMasterVO;
import egovframework.let.cop.bbs.service.EgovBBSLoneMasterService;
import egovframework.let.cop.com.service.BoardUseInf;

/**
 * 게시판 속성관리를 위한 서비스 구현 클래스
 * @author 공통 서비스 개발팀 한성곤
 * @since 2009.08.25
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.08.25  한성곤          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성 
 *  
 *  </pre>
 */
@Service("EgovBBSLoneMasterService")
@Transactional
public class EgovBBSLoneMasterServiceImpl implements EgovBBSLoneMasterService {

    @Resource(name = "BBSLoneMasterDAO")
    private BBSLoneMasterDAO masterDAO;

    @Resource(name = "BBSUseInfoManageDAO")
    private BBSUseInfoManageDAO bbsUseDAO;

    @Resource(name = "egovBBSMstrIdGnrService")
    private EgovIdGnrService idgenService;
		
    /**
     * 등록된 게시판 속성정보를 삭제한다.
     */
    public void deleteMaster(BoardMasterVO boardMaster) throws Exception {
	masterDAO.deleteMaster(boardMaster);
	
	BoardUseInf bdUseInf = new BoardUseInf();
	
	bdUseInf.setBbsId(boardMaster.getBbsId());
	bdUseInf.setLastUpdusrId(boardMaster.getLastUpdusrId());
	
	bbsUseDAO.deleteBBSUseInfByBoardId(bdUseInf);
    }

    /**
     * 신규 게시판 속성정보를 생성한다.
     */
    public String insertMaster(BoardMasterVO boardMaster) throws Exception {
	String bbsId = idgenService.getNextStringId();
	
	boardMaster.setBbsId(bbsId);
	
	masterDAO.insertMaster(boardMaster);

	//----------------------------------------------
	// 게시판 사용 등록 (시스템)
	//----------------------------------------------
	BoardUseInf bdUseInf = new BoardUseInf();

	bdUseInf.setBbsId(bbsId);
	bdUseInf.setTrgetId("SYSTEM_DEFAULT_BOARD");
	bdUseInf.setRegistSeCode("REGC01");
	bdUseInf.setFrstRegisterId(boardMaster.getFrstRegisterId());
	bdUseInf.setUseAt("Y");

	bbsUseDAO.insertBBSUseInf(bdUseInf);
	    
	return bbsId;
    }

    /**
     * 게시판 속성정보 한 건을 상세조회한다.
     */
    public BoardMasterVO selectMaster(BoardMasterVO searchVO) throws Exception {
	return masterDAO.selectMaster(searchVO);
    }

    /**
     * 게시판 속성 정보의 목록을 조회 한다.
     */
    public List<BoardMasterVO> selectMasterList(BoardMasterVO searchVO) throws Exception {
	return masterDAO.selectMasterList(searchVO);
    }

    /**
     * 게시판 속성정보를 수정한다.
     */
    public void updateMaster(BoardMasterVO boardMaster) throws Exception {
	masterDAO.updateMaster(boardMaster);
    }
}
