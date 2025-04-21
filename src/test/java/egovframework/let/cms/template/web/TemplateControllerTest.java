package egovframework.let.cms.template.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.template.service.TemplateService;
import egovframework.let.cms.template.service.dto.TemplateDto;
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
class TemplateControllerTest {

    @Mock
    private TemplateService templateService;

    @InjectMocks
    private TemplateController templateController;

    private TemplateDto testTemplateDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testTemplateDto = new TemplateDto();
        testTemplateDto.setName("Test Template");
        testTemplateDto.setContent("Test template content");
        testTemplateDto.setType("HTML");
        pageable = mock(Pageable.class);
    }

    @Test
    void createTemplate_Success() {
        // Given
        Long expectedId = 1L;
        when(templateService.createTemplate(any(TemplateDto.class))).thenReturn(expectedId);

        // When
        ResponseEntity<ApiResponse<Long>> response = templateController.createTemplate(testTemplateDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Long> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(expectedId, body.getData());
    }

    @Test
    void updateTemplate_Success() {
        // Given
        Long templateId = 1L;
        doNothing().when(templateService).updateTemplate(anyLong(), any(TemplateDto.class));

        // When
        ResponseEntity<ApiResponse<Void>> response = templateController.updateTemplate(templateId, testTemplateDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(templateService).updateTemplate(templateId, testTemplateDto);
    }

    @Test
    void deleteTemplate_Success() {
        // Given
        Long templateId = 1L;
        doNothing().when(templateService).deleteTemplate(anyLong());

        // When
        ResponseEntity<ApiResponse<Void>> response = templateController.deleteTemplate(templateId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(templateService).deleteTemplate(templateId);
    }

    @Test
    void getTemplate_Success() {
        // Given
        Long templateId = 1L;
        when(templateService.getTemplate(anyLong())).thenReturn(testTemplateDto);

        // When
        ResponseEntity<ApiResponse<TemplateDto>> response = templateController.getTemplate(templateId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<TemplateDto> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(testTemplateDto, body.getData());
    }

    @Test
    void getTemplates_Success() {
        // Given
        List<TemplateDto> templateList = Arrays.asList(testTemplateDto);
        Page<TemplateDto> templatePage = new PageImpl<>(templateList);
        when(templateService.getTemplates(any(Pageable.class))).thenReturn(templatePage);

        // When
        ResponseEntity<ApiResponse<Page<TemplateDto>>> response = templateController.getTemplates(pageable);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Page<TemplateDto>> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(templatePage, body.getData());
    }

    @Test
    void generatePreview_Success() {
        // Given
        Long templateId = 1L;
        String previewContent = "<div>Preview Content</div>";
        when(templateService.generatePreview(anyLong())).thenReturn(previewContent);

        // When
        ResponseEntity<ApiResponse<String>> response = templateController.generatePreview(templateId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<String> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertEquals(previewContent, body.getData());
    }

    @Test
    void applyTemplate_Success() {
        // Given
        Long templateId = 1L;
        Long targetId = 1L;
        String targetType = "CONTENT";
        doNothing().when(templateService).applyTemplate(anyLong(), anyLong(), anyString());

        // When
        ResponseEntity<ApiResponse<Void>> response = templateController.applyTemplate(templateId, targetId, targetType);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<Void> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        verify(templateService).applyTemplate(templateId, targetId, targetType);
    }
} 