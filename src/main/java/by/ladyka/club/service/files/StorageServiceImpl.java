package by.ladyka.club.service.files;

import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.entity.FileEntity;
import by.ladyka.club.repository.FilesRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final CustomSettings customSettings;
    private final FilesRepository filesRepository;

    @Override
    public String store(MultipartFile file) throws IOException {
        return store(file.getOriginalFilename(), file.getInputStream());
    }

    @Override
    public String store(String originalFilename, InputStream inputStream) throws IOException {
        String directoryOut = "2017";
        new File(customSettings.getFilesDirectory() + File.separator + directoryOut).mkdirs();
        String fileName = System.nanoTime() + originalFilename.substring(originalFilename.length() - 5);
        File outFile = new File(customSettings.getFilesDirectory() + File.separator + directoryOut + File.separator + fileName);
        OutputStream outStream = new FileOutputStream(outFile);
        IOUtils.copy(inputStream, outStream);
        outStream.close();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilePath(directoryOut + File.separator + fileName);
        fileEntity = filesRepository.save(fileEntity);
        return "/files/" + fileEntity.getFilePath();
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }
}