package egovframework.let.cop.bbs.web;

import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.cop.bbs.domain.BoardVO;
import egovframework.let.cop.bbs.service.EgovBBSManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "cms_04_BBS_Manage", description = "게시물 관리")
@RestController
@RequestMapping("/api/v1/bbs")
@RequiredArgsConstructor
public class GuestBookApiController {

    private final EgovBBSManageService bbsMngService;

    @Operation(
        summary = "방명록 목록 조회",
        description = "방명록의 목록을 조회합니다.",
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @GetMapping("/guest-books")
    public ResponseEntity<ResultVO> getGuestBookList(BoardVO boardVO) throws Exception {
        Map<String, Object> guestList = bbsMngService.selectGuestList(boardVO);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(guestList);
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "방명록 삭제",
        description = "방명록을 삭제합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @DeleteMapping("/guest-books/{nttId}")
    public ResponseEntity<ResultVO> deleteGuestBook(
        @Parameter(name = "nttId", description = "게시글 ID", in = ParameterIn.PATH)
        @PathVariable String nttId) throws Exception {
        
        BoardVO boardVO = new BoardVO();
        boardVO.setNttId(Long.parseLong(nttId));
        bbsMngService.deleteGuestList(boardVO);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }

    @Operation(
        summary = "방명록 비밀번호 조회",
        description = "방명록의 비밀번호를 조회합니다.",
        tags = {"cms_04_BBS_Manage"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
    })
    @GetMapping("/guest-books/{nttId}/password")
    public ResponseEntity<ResultVO> getGuestBookPassword(
        @Parameter(name = "nttId", description = "게시글 ID", in = ParameterIn.PATH)
        @PathVariable String nttId) throws Exception {
        
        BoardVO boardVO = new BoardVO();
        boardVO.setNttId(Long.parseLong(nttId));
        String password = bbsMngService.getPasswordInf(boardVO);
        
        Map<String, Object> result = new HashMap<>();
        result.put("password", password);
        
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(result);
        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        
        return ResponseEntity.ok(resultVO);
    }
} 