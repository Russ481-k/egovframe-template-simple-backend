package egovframework.let.cms.content.web;

import egovframework.let.cms.common.dto.ApiResponse;
import egovframework.let.cms.content.service.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private FileController fileController;

    private MockMultipartFile testFile;
    private Path mockPath;

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "Test file content".getBytes()
        );
        mockPath = Paths.get("test.txt");
    }

    @Test
    void uploadFile_Success() {
        // Given
        String expectedFilename = "test.txt";
        when(fileStorageService.store(any(MultipartFile.class))).thenReturn(expectedFilename);

        // When
        ResponseEntity<ApiResponse<String>> response = fileController.uploadFile(testFile);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        ApiResponse<String> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertTrue(body.getData().contains(expectedFilename));
    }

    @Test
    void downloadFile_Success() throws Exception {
        // Given
        String filename = "test.txt";
        when(fileStorageService.load(filename)).thenReturn(mockPath);
        Resource mockResource = mock(Resource.class);
        when(mockResource.exists()).thenReturn(true);
        when(mockResource.isReadable()).thenReturn(true);
        when(mockResource.getFilename()).thenReturn(filename);

        // When
        ResponseEntity<Resource> response = fileController.downloadFile(filename);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getHeaders().containsKey(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    void previewImage_Success() throws Exception {
        // Given
        String filename = "test.jpg";
        when(fileStorageService.load(filename)).thenReturn(mockPath);
        Resource mockResource = mock(Resource.class);
        when(mockResource.exists()).thenReturn(true);
        when(mockResource.isReadable()).thenReturn(true);

        // When
        ResponseEntity<Resource> response = fileController.previewImage(filename);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
    }
} 
 
 
 