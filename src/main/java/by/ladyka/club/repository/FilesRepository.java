package by.ladyka.club.repository;

import by.ladyka.club.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findFirstByFilePath(String filePath);
}