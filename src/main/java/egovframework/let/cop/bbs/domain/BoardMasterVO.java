package egovframework.let.cop.bbs.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 게시판 속성 정보를 관리하기 위한 VO  클래스
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
@Schema(description = "게시판 속성 정보 VO")
@Getter
@Setter
public class BoardMasterVO implements Serializable {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -8070768280461816170L;

	@Schema(description = "게시판 아이디")
	private String bbsId = "";

	@Schema(description = "게시판 유형 코드")
	private String bbsTyCode = "";

	@Schema(description = "게시판 속성 코드")
	private String bbsAttrbCode = "";

	@Schema(description = "게시판 명")
	private String bbsNm = "";

	@Schema(description = "게시판 소개")
	private String bbsIntrcn = "";

	@Schema(description = "답장 가능 여부")
	private String replyPosblAt = "";

	@Schema(description = "파일 첨부 가능 여부")
	private String fileAtchPosblAt = "";

	@Schema(description = "첨부 가능 파일 수")
	private int posblAtchFileNumber = 0;

	@Schema(description = "첨부 가능 파일 사이즈")
	private String posblAtchFileSize = "";

	@Schema(description = "템플릿 아이디")
	private String tmplatId = "";

	@Schema(description = "사용 여부")
	private String useAt = "";

	@Schema(description = "커뮤니티 아이디")
	private String cmmntyId = "";

	@Schema(description = "최초 등록자 아이디")
	private String frstRegisterId = "";

	@Schema(description = "최초 등록 시점")
	private String frstRegisterPnttm = "";

	@Schema(description = "최종 수정자 아이디")
	private String lastUpdusrId = "";

	@Schema(description = "최종 수정 시점")
	private String lastUpdusrPnttm = "";

	@Schema(description = "검색시작일")
	private String searchBgnDe = "";

	@Schema(description = "검색조건")
	private String searchCnd = "";

	@Schema(description = "검색종료일")
	private String searchEndDe = "";

	@Schema(description = "검색단어")
	private String searchWrd = "";

	@Schema(description = "정렬순서(DESC,ASC)")
	private String sortOrdr = "";

	@Schema(description = "검색사용여부")
	private String searchUseYn = "";

	@Schema(description = "현재페이지")
	private int pageIndex = 1;

	@Schema(description = "페이지갯수")
	private int pageUnit = 10;

	@Schema(description = "페이지사이즈")
	private int pageSize = 10;

	@Schema(description = "첫페이지 인덱스")
	private int firstIndex = 1;

	@Schema(description = "마지막페이지 인덱스")
	private int lastIndex = 1;

	@Schema(description = "페이지당 레코드 개수")
	private int recordCountPerPage = 10;

	@Schema(description = "레코드 번호")
	private int rowNo = 0;

	@Schema(description = "최초 등록자명")
	private String frstRegisterNm = "";

	@Schema(description = "게시판유형 코드명")
	private String bbsTyCodeNm = "";

	@Schema(description = "게시판속성 코드명")
	private String bbsAttrbCodeNm = "";

	@Schema(description = "최종 수정자명")
	private String lastUpdusrNm = "";

	@Schema(description = "권한지정 여부")
	private String authFlag = "";

	@Schema(description = "게시판사용여부")
	private String bbsUseFlag = "";

	@Schema(description = "대상 아이디")
	private String trgetId = "";

	@Schema(description = "등록구분코드")
	private String registSeCode = "";

	@Schema(description = "옵션")
	private String option = "";

	@Schema(description = "댓글여부")
	private String commentAt = "";

	@Schema(description = "만족도조사여부")
	private String stsfdgAt = "";

	@Schema(description = "사용자 ID")
	private String uniqId = "";

	/**
	 * toString 메소드를 대치한다.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getBbsUseFlag() {
		return bbsUseFlag;
	}

	public void setBbsUseFlag(String bbsUseFlag) {
		this.bbsUseFlag = bbsUseFlag;
	}

	public String getTrgetId() {
		return trgetId;
	}

	public void setTrgetId(String trgetId) {
		this.trgetId = trgetId;
	}
}
