package egovframework.let.cop.bbs.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 게시판 속성정보를 담기위한 엔티티 클래스
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
@Schema(description = "게시판 속성 정보 엔티티")
@Getter
@Setter
public class BoardMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 게시판 아이디
     */
    @Schema(description = "게시판 아이디")
    private String bbsId;

    /**
     * 게시판 유형 코드
     */
    @Schema(description = "게시판 유형 코드")
    private String bbsTyCode;

    /**
     * 게시판 속성 코드
     */
    @Schema(description = "게시판 속성 코드")
    private String bbsAttrbCode;

    /**
     * 게시판 이름
     */
    @Schema(description = "게시판 이름")
    private String bbsNm;

    /**
     * 게시판 설명
     */
    @Schema(description = "게시판 설명")
    private String bbsIntrcn;

    /**
     * 댓글 가능 여부
     */
    @Schema(description = "댓글 가능 여부")
    private String replyPosblAt;

    /**
     * 파일 첨부 가능 여부
     */
    @Schema(description = "파일 첨부 가능 여부")
    private String fileAtchPosblAt;

    /**
     * 첨부 가능 파일 수
     */
    @Schema(description = "첨부 가능 파일 수")
    private int posblAtchFileNumber;

    /**
     * 첨부 가능 파일 크기
     */
    @Schema(description = "첨부 가능 파일 크기")
    private String posblAtchFileSize;

    /**
     * 템플릿 아이디
     */
    @Schema(description = "템플릿 아이디")
    private String tmplatId;

    /**
     * 사용 여부
     */
    @Schema(description = "사용 여부")
    private String useAt;

    /**
     * 최초 등록자 아이디
     */
    @Schema(description = "최초 등록자 아이디")
    private String frstRegisterId;

    /**
     * 최초 등록 시점
     */
    @Schema(description = "최초 등록 시점")
    private String frstRegisterPnttm;

    /**
     * 최종 수정자 아이디
     */
    @Schema(description = "최종 수정자 아이디")
    private String lastUpdusrId;

    /**
     * 최종 수정 시점
     */
    @Schema(description = "최종 수정 시점")
    private String lastUpdtPnttm;

    /**
     * 추가 옵션
     */
    @Schema(description = "추가 옵션")
    private String option;

    /**
     * toString 메소드
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
} 