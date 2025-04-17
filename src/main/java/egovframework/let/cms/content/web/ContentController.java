package egovframework.let.cms.content.web;

import egovframework.let.cms.content.service.ContentService;
import egovframework.let.cms.content.service.dto.ContentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("contentController")
@RequestMapping("/api/cms/content")
@RequiredArgsConstructor
@Tag(name = "Cms03Content", description = "컨텐츠 관리 API")
public class ContentController {

    private final ContentService contentService;

    @Operation(summary = "컨텐츠 생성", description = "새로운 컨텐츠를 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<Long> createContent(@RequestBody ContentDto contentDto) {
        Long contentId = contentService.createContent(contentDto);
        return ResponseEntity.ok(contentId);
    }

    @Operation(summary = "컨텐츠 수정", description = "기존 컨텐츠를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "수정 성공"),
        @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @PutMapping("/{contentId}")
    public ResponseEntity<Void> updateContent(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId,
        @RequestBody ContentDto contentDto) {
        contentService.updateContent(contentId, contentDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "컨텐츠 삭제", description = "컨텐츠를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> deleteContent(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId) {
        contentService.deleteContent(contentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "컨텐츠 조회", description = "특정 컨텐츠를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDto> getContent(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId) {
        contentService.increaseViewCount(contentId);
        return ResponseEntity.ok(contentService.getContent(contentId));
    }

    @Operation(summary = "컨텐츠 목록 조회", description = "컨텐츠 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<Page<ContentDto>> getContents(Pageable pageable) {
        return ResponseEntity.ok(contentService.getContents(pageable));
    }

    @Operation(summary = "컨텐츠 검색", description = "키워드로 컨텐츠를 검색합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<ContentDto>> searchContents(
        @Parameter(description = "검색 키워드") @RequestParam String keyword,
        Pageable pageable) {
        return ResponseEntity.ok(contentService.searchContents(keyword, pageable));
    }

    @Operation(summary = "타입별 컨텐츠 조회", description = "특정 타입의 컨텐츠를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/type/{contentType}")
    public ResponseEntity<Page<ContentDto>> getContentsByType(
        @Parameter(description = "컨텐츠 타입") @PathVariable String contentType,
        Pageable pageable) {
        return ResponseEntity.ok(contentService.getContentsByType(contentType, pageable));
    }

    @Operation(summary = "컨텐츠 버전 생성", description = "컨텐츠의 현재 버전을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "버전 생성 성공"),
        @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없음")
    })
    @PostMapping("/{contentId}/versions")
    public ResponseEntity<Long> createVersion(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId) {
        return ResponseEntity.ok(contentService.createVersion(contentId));
    }

    @Operation(summary = "컨텐츠 버전 복원", description = "특정 버전으로 컨텐츠를 복원합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "복원 성공"),
        @ApiResponse(responseCode = "404", description = "컨텐츠 또는 버전을 찾을 수 없음")
    })
    @PostMapping("/{contentId}/versions/{versionId}/restore")
    public ResponseEntity<Void> restoreVersion(
        @Parameter(description = "컨텐츠 ID") @PathVariable Long contentId,
        @Parameter(description = "버전 ID") @PathVariable Long versionId) {
        contentService.restoreVersion(contentId, versionId);
        return ResponseEntity.ok().build();
    }
} 