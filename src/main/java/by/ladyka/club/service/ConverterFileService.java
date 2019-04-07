package by.ladyka.club.service;

import by.ladyka.club.entity.FileEntity;
import by.ladyka.club.repository.FilesRepository;
import by.ladyka.merchshop.dto.ImageDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterFileService {


    public ImageDTO toDtoImage(FileEntity imageEntity) {
        ImageDTO imageDTO = new ImageDTO();
        BeanUtils.copyProperties(imageEntity, imageDTO);
        imageDTO.setPath(imageEntity.getFilePath());
        return imageDTO;
    }

}
