package egovframework.let.cms.content.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.content.service.ContentService;
import egovframework.let.cms.content.service.dto.ContentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
@Tag(name = "cms_03_Content", description = "컨텐츠 관리 API")
public class ContentController {

    private final ContentService contentService;

    @Operation(summary = "콘텐츠 생성", description = "새로운 컨텐츠를 생성합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createContent(@RequestBody ContentDto contentDto) {
        Long contentId = contentService.createContent(contentDto);
        return ResponseEntity.ok(ApiResponse.success("콘텐츠가 성공적으로 생성되었습니다.", contentId));
    }

    @Operation(summary = "콘텐츠 수정", description = "기존 컨텐츠를 수정합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @PutMapping("/{contentId}")
    public ResponseEntity<ApiResponse<Void>> updateContent(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId,
        @RequestBody ContentDto contentDto) {
        contentService.updateContent(contentId, contentDto);
        return ResponseEntity.ok(ApiResponse.success("콘텐츠가 성공적으로 수정되었습니다.", null));
    }

    @Operation(summary = "콘텐츠 삭제", description = "컨텐츠를 삭제합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ApiResponse<Void>> deleteContent(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId) {
        contentService.deleteContent(contentId);
        return ResponseEntity.ok(ApiResponse.success("콘텐츠가 성공적으로 삭제되었습니다.", null));
    }

    @Operation(summary = "콘텐츠 조회", description = "특정 컨텐츠를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @GetMapping("/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> getContent(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId) {
        contentService.increaseViewCount(contentId);
        return ResponseEntity.ok(ApiResponse.success("콘텐츠를 성공적으로 조회했습니다.", contentService.getContent(contentId)));
    }

    @Operation(summary = "콘텐츠 목록 조회", description = "컨텐츠 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ContentDto>>> getContents(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("콘텐츠 목록을 성공적으로 조회했습니다.", contentService.getContents(pageable)));
    }

    @Operation(summary = "콘텐츠 검색", description = "키워드로 컨텐츠를 검색합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "검색 성공")
    })
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ContentDto>>> searchContents(
        @Parameter(description = "검색 키워드") @RequestParam String keyword,
        Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("콘텐츠 검색이 완료되었습니다.", contentService.searchContents(keyword, pageable)));
    }

    @Operation(summary = "타입별 컨텐츠 조회", description = "특정 타입의 컨텐츠를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/type/{contentType}")
    public ResponseEntity<ApiResponse<Page<ContentDto>>> getContentsByType(
        @Parameter(description = "컨텐츠 타입") @PathVariable String contentType,
        Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("타입별 콘텐츠를 성공적으로 조회했습니다.", contentService.getContentsByType(contentType, pageable)));
    }

    @Operation(summary = "콘텐츠 버전 생성", description = "컨텐츠의 현재 버전을 생성합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "버전 생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @PostMapping("/{contentId}/versions")
    public ResponseEntity<ApiResponse<Long>> createVersion(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId) {
        return ResponseEntity.ok(ApiResponse.success("콘텐츠 버전이 성공적으로 생성되었습니다.", contentService.createVersion(contentId)));
    }

    @Operation(summary = "콘텐츠 버전 복원", description = "특정 버전으로 컨텐츠를 복원합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "복원 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "컨텐츠 또는 버전을 찾을 수 없음")
    })
    @PostMapping("/{contentId}/versions/{versionId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreVersion(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId,
        @Parameter(description = "버전 ID") @PathVariable Long versionId) {
        contentService.restoreVersion(contentId, versionId);
        return ResponseEntity.ok(ApiResponse.success("콘텐츠가 성공적으로 복원되었습니다.", null));
    }
} 