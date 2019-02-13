package by.ladyka.club.service.files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface StorageService {
    String store(MultipartFile file) throws IOException;
    String store(String originalFilename, InputStream inputStream) throws IOException;
    Path load(String filename);
    Resource loadAsResource(String filename);
}