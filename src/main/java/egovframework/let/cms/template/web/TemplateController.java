package egovframework.let.cms.template.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.template.service.TemplateService;
import egovframework.let.cms.template.service.dto.TemplateDto;
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
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
@Tag(name = "Template", description = "템플릿 관리 API")
public class TemplateController {

    private final TemplateService templateService;

    @Operation(summary = "템플릿 생성", description = "새로운 템플릿을 생성합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createTemplate(@RequestBody TemplateDto templateDto) {
        Long templateId = templateService.createTemplate(templateDto);
        return ResponseEntity.ok(ApiResponse.success("템플릿이 성공적으로 생성되었습니다.", templateId));
    }

    @Operation(summary = "템플릿 수정", description = "기존 템플릿을 수정합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "템플릿을 찾을 수 없음")
    })
    @PutMapping("/{templateId}")
    public ResponseEntity<ApiResponse<Void>> updateTemplate(
        @Parameter(description = "템플릿 ID") @PathVariable Long templateId,
        @RequestBody TemplateDto templateDto) {
        templateService.updateTemplate(templateId, templateDto);
        return ResponseEntity.ok(ApiResponse.success("템플릿이 성공적으로 수정되었습니다.", null));
    }

    @Operation(summary = "템플릿 삭제", description = "템플릿을 삭제합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "템플릿을 찾을 수 없음")
    })
    @DeleteMapping("/{templateId}")
    public ResponseEntity<ApiResponse<Void>> deleteTemplate(
        @Parameter(description = "템플릿 ID") @PathVariable Long templateId) {
        templateService.deleteTemplate(templateId);
        return ResponseEntity.ok(ApiResponse.success("템플릿이 성공적으로 삭제되었습니다.", null));
    }

    @Operation(summary = "템플릿 조회", description = "특정 템플릿을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "템플릿을 찾을 수 없음")
    })
    @GetMapping("/{templateId}")
    public ResponseEntity<ApiResponse<TemplateDto>> getTemplate(
        @Parameter(description = "템플릿 ID") @PathVariable Long templateId) {
        return ResponseEntity.ok(ApiResponse.success("템플릿을 성공적으로 조회했습니다.", templateService.getTemplate(templateId)));
    }

    @Operation(summary = "템플릿 목록 조회", description = "템플릿 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TemplateDto>>> getTemplates(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("템플릿 목록을 성공적으로 조회했습니다.", templateService.getTemplates(pageable)));
    }

    @Operation(summary = "템플릿 미리보기", description = "템플릿의 미리보기를 생성합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "미리보기 생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "템플릿을 찾을 수 없음")
    })
    @GetMapping("/{templateId}/preview")
    public ResponseEntity<ApiResponse<String>> generatePreview(
        @Parameter(description = "템플릿 ID") @PathVariable Long templateId) {
        return ResponseEntity.ok(ApiResponse.success("템플릿 미리보기가 성공적으로 생성되었습니다.", templateService.generatePreview(templateId)));
    }

    @Operation(summary = "템플릿 적용", description = "템플릿을 특정 대상에 적용합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "적용 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "템플릿 또는 대상을 찾을 수 없음")
    })
    @PostMapping("/{templateId}/apply")
    public ResponseEntity<ApiResponse<Void>> applyTemplate(
        @Parameter(description = "템플릿 ID") @PathVariable Long templateId,
        @Parameter(description = "적용 대상 ID") @RequestParam Long targetId,
        @Parameter(description = "적용 대상 타입") @RequestParam String targetType) {
        templateService.applyTemplate(templateId, targetId, targetType);
        return ResponseEntity.ok(ApiResponse.success("템플릿이 성공적으로 적용되었습니다.", null));
    }
} 