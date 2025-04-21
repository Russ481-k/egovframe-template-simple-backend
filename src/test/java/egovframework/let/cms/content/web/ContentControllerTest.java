package egovframework.let.cms.content.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.content.service.ContentService;
import egovframework.let.cms.content.service.dto.ContentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private ContentController contentController;

    private ContentDto testContentDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testContentDto = new ContentDto();
        testContentDto.setTitle("Test Content");
        testContentDto.setContent("Test content body");
        testContentDto.setType("ARTICLE");
        pageable = mock(Pageable.class);
    }

    @Test
    void createContent_Success() {
        // Given
        Long expectedId = 1L;
        when(contentService.createContent(any(ContentDto.class))).thenReturn(expectedId);

        // When
        ResponseEntity<ApiResponse<Long>> response = contentController.createContent(testContentDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Long> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(expectedId, body.getData());
    }

    @Test
    void updateContent_Success() {
        // Given
        Long contentId = 1L;
        doNothing().when(contentService).updateContent(anyLong(), any(ContentDto.class));

        // When
        ResponseEntity<ApiResponse<Void>> response = contentController.updateContent(contentId, testContentDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(contentService).updateContent(contentId, testContentDto);
    }

    @Test
    void deleteContent_Success() {
        // Given
        Long contentId = 1L;
        doNothing().when(contentService).deleteContent(anyLong());

        // When
        ResponseEntity<ApiResponse<Void>> response = contentController.deleteContent(contentId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(contentService).deleteContent(contentId);
    }

    @Test
    void getContent_Success() {
        // Given
        Long contentId = 1L;
        when(contentService.getContent(anyLong())).thenReturn(testContentDto);
        doNothing().when(contentService).increaseViewCount(anyLong());

        // When
        ResponseEntity<ApiResponse<ContentDto>> response = contentController.getContent(contentId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<ContentDto> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(testContentDto, body.getData());
        verify(contentService).increaseViewCount(contentId);
    }

    @Test
    void getContents_Success() {
        // Given
        List<ContentDto> contentList = Arrays.asList(testContentDto);
        Page<ContentDto> contentPage = new PageImpl<>(contentList);
        when(contentService.getContents(any(Pageable.class))).thenReturn(contentPage);

        // When
        ResponseEntity<ApiResponse<Page<ContentDto>>> response = contentController.getContents(pageable);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Page<ContentDto>> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(contentPage, body.getData());
    }

    @Test
    void searchContents_Success() {
        // Given
        String keyword = "test";
        List<ContentDto> contentList = Arrays.asList(testContentDto);
        Page<ContentDto> contentPage = new PageImpl<>(contentList);
        when(contentService.searchContents(anyString(), any(Pageable.class))).thenReturn(contentPage);

        // When
        ResponseEntity<ApiResponse<Page<ContentDto>>> response = contentController.searchContents(keyword, pageable);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Page<ContentDto>> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(contentPage, body.getData());
    }

    @Test
    void getContentsByType_Success() {
        // Given
        String contentType = "ARTICLE";
        List<ContentDto> contentList = Arrays.asList(testContentDto);
        Page<ContentDto> contentPage = new PageImpl<>(contentList);
        when(contentService.getContentsByType(anyString(), any(Pageable.class))).thenReturn(contentPage);

        // When
        ResponseEntity<ApiResponse<Page<ContentDto>>> response = contentController.getContentsByType(contentType, pageable);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Page<ContentDto>> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(contentPage, body.getData());
    }

    @Test
    void createVersion_Success() {
        // Given
        Long contentId = 1L;
        Long versionId = 1L;
        when(contentService.createVersion(anyLong())).thenReturn(versionId);

        // When
        ResponseEntity<ApiResponse<Long>> response = contentController.createVersion(contentId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Long> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(versionId, body.getData());
    }

    @Test
    void restoreVersion_Success() {
        // Given
        Long contentId = 1L;
        Long versionId = 1L;
        doNothing().when(contentService).restoreVersion(anyLong(), anyLong());

        // When
        ResponseEntity<ApiResponse<Void>> response = contentController.restoreVersion(contentId, versionId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(contentService).restoreVersion(contentId, versionId);
    }
} 
 
 
 