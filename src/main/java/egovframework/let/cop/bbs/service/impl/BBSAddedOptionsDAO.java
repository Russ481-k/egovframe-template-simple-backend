package egovframework.let.cop.bbs.service.impl;

import egovframework.let.cop.bbs.domain.BoardMasterVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 2단계 기능 추가 (댓글관리, 만족도조사) 관리를 위한 데이터 접근 클래스
 * @author 공통 서비스 개발팀 한성곤
 * @since 2009.06.26
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.06.26  한성곤          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성 
 *  
 *  </pre>
 */
@Repository("BBSAddedOptionsDAO")
@Mapper
public interface BBSAddedOptionsDAO {
    void insertAddedOptionsInf(BoardMasterVO boardMaster) throws Exception;
    BoardMasterVO selectAddedOptionsInf(BoardMasterVO boardMaster) throws Exception;
}
