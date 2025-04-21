package egovframework.let.cop.bbs.web;

import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.cop.bbs.domain.BoardMasterVO;
import egovframework.let.cop.bbs.service.EgovBBSAttributeManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "cms_04_BBS_Manage", description = "게시물 관리")
@RestController
@RequestMapping("/api/v1/bbs")
@RequiredArgsConstructor
public class BBSAttributeManageApiController {

    private final EgovBBSAttributeManageService bbsAttrbService;

    @Operation(
        summary = "게시판 생성",
        description = "새로운 게시판을 생성합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "생성 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @PostMapping("/boards")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultVO> createBoard(@RequestBody BoardMasterVO boardMaster) throws Exception {
        String bbsId = bbsAttrbService.insertBBSMastetInf(boardMaster);
        
        Map<String, Object> result = new HashMap<>();
        result.put("bbsId", bbsId);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(result);
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 수정",
        description = "기존 게시판의 속성을 수정합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "수정 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @PutMapping("/boards/{bbsId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultVO> updateBoard(
        @Parameter(name = "bbsId", description = "게시판 ID", in = ParameterIn.PATH)
        @PathVariable String bbsId,
        @RequestBody BoardMasterVO boardMaster) throws Exception {
        
        boardMaster.setBbsId(bbsId);
        bbsAttrbService.updateBBSMasterInf(boardMaster);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 삭제",
        description = "기존 게시판을 삭제합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @DeleteMapping("/boards/{bbsId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultVO> deleteBoard(
        @Parameter(name = "bbsId", description = "게시판 ID", in = ParameterIn.PATH)
        @PathVariable String bbsId) throws Exception {
        
        BoardMasterVO boardMaster = new BoardMasterVO();
        boardMaster.setBbsId(bbsId);
        bbsAttrbService.deleteBBSMasterInf(boardMaster);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 상세 조회",
        description = "특정 게시판의 상세 정보를 조회합니다.",
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @GetMapping("/boards/{bbsId}")
    public ResponseEntity<ResultVO> getBoard(
        @Parameter(name = "bbsId", description = "게시판 ID", in = ParameterIn.PATH)
        @PathVariable String bbsId) throws Exception {
        
        BoardMasterVO searchVO = new BoardMasterVO();
        searchVO.setBbsId(bbsId);
        BoardMasterVO boardMaster = bbsAttrbService.selectBBSMasterInf(searchVO);
        
        Map<String, Object> result = new HashMap<>();
        result.put("boardMaster", boardMaster);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(result);
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 목록 조회",
        description = "모든 게시판의 목록을 조회합니다.",
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @GetMapping("/boards")
    public ResponseEntity<ResultVO> getBoardList(BoardMasterVO searchVO) throws Exception {
        List<BoardMasterVO> boardList = bbsAttrbService.selectAllBBSMasteInf(searchVO);
        
        Map<String, Object> result = new HashMap<>();
        result.put("boardList", boardList);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(result);
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }
} 