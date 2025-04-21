package egovframework.let.cms.menu.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.menu.service.MenuService;
import egovframework.let.cms.menu.service.dto.MenuDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Tag(name = "cms_01_Menu", description = "메뉴 관리 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 생성", description = "새로운 메뉴를 생성합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMenu(@RequestBody MenuDto menuDto) {
        Long menuId = menuService.createMenu(menuDto);
        return ResponseEntity.ok(ApiResponse.success("메뉴가 성공적으로 생성되었습니다.", menuId));
    }

    @Operation(summary = "메뉴 수정", description = "기존 메뉴를 수정합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음")
    })
    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> updateMenu(
        @Parameter(description = "메뉴 ID") @PathVariable Long menuId,
        @RequestBody MenuDto menuDto) {
        menuService.updateMenu(menuId, menuDto);
        return ResponseEntity.ok(ApiResponse.success("메뉴가 성공적으로 수정되었습니다.", null));
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음")
    })
    @DeleteMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(
        @Parameter(description = "메뉴 ID") @PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok(ApiResponse.success("메뉴가 성공적으로 삭제되었습니다.", null));
    }

    @Operation(summary = "메뉴 조회", description = "특정 메뉴를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음")
    })
    @GetMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuDto>> getMenu(
        @Parameter(description = "메뉴 ID") @PathVariable Long menuId) {
        return ResponseEntity.ok(ApiResponse.success("메뉴 정보를 성공적으로 조회했습니다.", menuService.getMenu(menuId)));
    }

    @Operation(summary = "메뉴 트리 조회", description = "메뉴 트리 구조를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<MenuDto>>> getMenuTree() {
        return ResponseEntity.ok(ApiResponse.success("메뉴 트리를 성공적으로 조회했습니다.", menuService.getMenuTree()));
    }

    @Operation(summary = "메뉴 순서 변경", description = "메뉴의 순서를 변경합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "변경 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음")
    })
    @PutMapping("/{menuId}/order")
    public ResponseEntity<ApiResponse<Void>> updateMenuOrder(
        @Parameter(description = "메뉴 ID") @PathVariable Long menuId,
        @Parameter(description = "새로운 순서") @RequestParam int newOrder) {
        menuService.updateMenuOrder(menuId, newOrder);
        return ResponseEntity.ok(ApiResponse.success("메뉴 순서가 성공적으로 변경되었습니다.", null));
    }

    @Operation(summary = "메뉴 활성화 상태 변경", description = "메뉴의 활성화 상태를 변경합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "변경 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음")
    })
    @PutMapping("/{menuId}/active")
    public ResponseEntity<ApiResponse<Void>> updateMenuActive(
        @Parameter(description = "메뉴 ID") @PathVariable Long menuId,
        @Parameter(description = "활성화 여부") @RequestParam boolean isActive) {
        menuService.updateMenuActive(menuId, isActive);
        return ResponseEntity.ok(ApiResponse.success( "메뉴 활성화 상태가 성공적으로 변경되었습니다.",null));
    }
} 