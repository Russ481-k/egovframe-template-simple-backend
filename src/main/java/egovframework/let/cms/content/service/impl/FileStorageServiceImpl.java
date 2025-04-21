package egovframework.let.cms.content.service.impl;

import egovframework.let.cms.content.exception.FileStorageException;
import egovframework.let.cms.content.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl() {
        this.fileStorageLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("파일 저장 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + "_" + originalFilename;

        try {
            if (filename.contains("..")) {
                throw new FileStorageException("파일명에 잘못된 문자가 포함되어 있습니다: " + filename);
            }

            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException ex) {
            throw new FileStorageException("파일을 저장할 수 없습니다: " + filename, ex);
        }
    }

    @Override
    public Path load(String filename) {
        return fileStorageLocation.resolve(filename).normalize();
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("파일을 읽을 수 없습니다: " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("파일 URL이 잘못되었습니다: " + filename, ex);
        }
    }
} 