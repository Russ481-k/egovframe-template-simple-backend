package egovframework.let.cop.bbs.web;

import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.cop.com.service.BoardUseInf;
import egovframework.let.cop.bbs.domain.BoardUseInfVO;
import egovframework.let.cop.bbs.service.EgovBBSUseInfoManageService;
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
import java.util.Map;

@Tag(name = "cms_04_BBS_Manage", description = "게시물 관리")
@RestController
@RequestMapping("/api/v1/bbs")
@RequiredArgsConstructor
public class BBSUseInfoManageApiController {

    private final EgovBBSUseInfoManageService bbsUseService;

    @Operation(
        summary = "게시판 사용 정보 등록",
        description = "새로운 게시판 사용 정보를 등록합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "등록 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @PostMapping("/use-info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultVO> createBBSUseInfo(@RequestBody BoardUseInf boardUseInf) throws Exception {
        bbsUseService.insertBBSUseInf(boardUseInf);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 사용 정보 수정",
        description = "기존 게시판 사용 정보를 수정합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "수정 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @PutMapping("/use-info/{bbsId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultVO> updateBBSUseInfo(
        @Parameter(name = "bbsId", description = "게시판 ID", in = ParameterIn.PATH)
        @PathVariable String bbsId,
        @RequestBody BoardUseInf boardUseInf) throws Exception {
        
        boardUseInf.setBbsId(bbsId);
        bbsUseService.updateBBSUseInf(boardUseInf);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 사용 정보 삭제",
        description = "기존 게시판 사용 정보를 삭제합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @DeleteMapping("/use-info/{bbsId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultVO> deleteBBSUseInfo(
        @Parameter(name = "bbsId", description = "게시판 ID", in = ParameterIn.PATH)
        @PathVariable String bbsId) throws Exception {
        
        BoardUseInf boardUseInf = new BoardUseInf();
        boardUseInf.setBbsId(bbsId);
        bbsUseService.deleteBBSUseInf(boardUseInf);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "게시판 사용 정보 조회",
        description = "특정 게시판의 사용 정보를 조회합니다.",
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @GetMapping("/use-info/{bbsId}")
    public ResponseEntity<ResultVO> getBBSUseInfo(
        @Parameter(name = "bbsId", description = "게시판 ID", in = ParameterIn.PATH)
        @PathVariable String bbsId,
        BoardUseInfVO searchVO) throws Exception {
        
        searchVO.setBbsId(bbsId);
        Map<String, Object> useInfo = bbsUseService.selectBBSUseInfs(searchVO);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(useInfo);
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }
} 