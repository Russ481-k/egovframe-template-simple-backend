package egovframework.let.cms.menu.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.menu.service.MenuService;
import egovframework.let.cms.menu.service.dto.MenuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private MenuDto testMenuDto;

    @BeforeEach
    void setUp() {
        testMenuDto = new MenuDto();
        testMenuDto.setMenuNm("Test Menu");
        testMenuDto.setMenuUrl("/test");
        testMenuDto.setMenuOrdr(1);
        testMenuDto.setUseAt("Y");
    }

    @Test
    void createMenu_Success() {
        // Given
        Long expectedId = 1L;
        when(menuService.createMenu(any(MenuDto.class))).thenReturn(expectedId);

        // When
        ResponseEntity<ApiResponse<Long>> response = menuController.createMenu(testMenuDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Long> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(expectedId, body.getData());
    }

    @Test
    void updateMenu_Success() {
        // Given
        Long menuId = 1L;
        doNothing().when(menuService).updateMenu(anyLong(), any(MenuDto.class));

        // When
        ResponseEntity<ApiResponse<Void>> response = menuController.updateMenu(menuId, testMenuDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(menuService).updateMenu(menuId, testMenuDto);
    }

    @Test
    void deleteMenu_Success() {
        // Given
        Long menuId = 1L;
        doNothing().when(menuService).deleteMenu(anyLong());

        // When
        ResponseEntity<ApiResponse<Void>> response = menuController.deleteMenu(menuId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(menuService).deleteMenu(menuId);
    }

    @Test
    void getMenu_Success() {
        // Given
        Long menuId = 1L;
        when(menuService.getMenu(anyLong())).thenReturn(testMenuDto);

        // When
        ResponseEntity<ApiResponse<MenuDto>> response = menuController.getMenu(menuId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<MenuDto> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(testMenuDto, body.getData());
    }

    @Test
    void getMenuTree_Success() {
        // Given
        List<MenuDto> menuTree = Arrays.asList(testMenuDto);
        when(menuService.getMenuTree()).thenReturn(menuTree);

        // When
        ResponseEntity<ApiResponse<List<MenuDto>>> response = menuController.getMenuTree();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<List<MenuDto>> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(menuTree, body.getData());
    }

    @Test
    void updateMenuOrder_Success() {
        // Given
        Long menuId = 1L;
        int newOrder = 2;
        doNothing().when(menuService).updateMenuOrder(anyLong(), anyInt());

        // When
        ResponseEntity<ApiResponse<Void>> response = menuController.updateMenuOrder(menuId, newOrder);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(menuService).updateMenuOrder(menuId, newOrder);
    }

    @Test
    void updateMenuActive_Success() {
        // Given
        Long menuId = 1L;
        boolean isActive = false;
        doNothing().when(menuService).updateMenuActive(anyLong(), anyBoolean());

        // When
        ResponseEntity<ApiResponse<Void>> response = menuController.updateMenuActive(menuId, isActive);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(menuService).updateMenuActive(menuId, isActive);
    }
} 
 
 
 