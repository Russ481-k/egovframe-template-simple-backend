package egovframework.let.cms.content.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {
    String store(MultipartFile file);
    Path load(String filename);
    Resource loadAsResource(String filename);
} 