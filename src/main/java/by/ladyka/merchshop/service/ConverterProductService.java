package by.ladyka.merchshop.service;

import by.ladyka.club.entity.FileEntity;
import by.ladyka.club.repository.FilesRepository;
import by.ladyka.club.service.ConverterFileService;
import by.ladyka.merchshop.dto.ImageDTO;
import by.ladyka.merchshop.dto.ProductDTO;
import by.ladyka.merchshop.entity.Product;
import by.ladyka.merchshop.entity.ProductCategory;
import by.ladyka.merchshop.repository.ProductCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConverterProductService {

    @Autowired
    private ConverterProductCategoryService converterProductCategoryService;

    @Autowired
    private ConverterFileService converterFileService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private FilesRepository filesRepository;

    public ProductDTO toDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        productDTO.setProductCategory(converterProductCategoryService.toDto(product.getProductCategory()));
        List<ImageDTO> imageDTOs = product.getImages().stream().map((imageEntity) -> converterFileService.toDtoImage(imageEntity)).collect(Collectors.toList());
        productDTO.setImages(imageDTOs);
        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO, Product product) {
        BeanUtils.copyProperties(productDTO, product);
        product.setProductCategory(productCategoryRepository.getOne(productDTO.getProductCategory().getId()));
        product.setImages(
                productDTO.getImages().stream().map(
                        imageDTO -> {
                            if(imageDTO.getId() != null){
                                return filesRepository.getOne(imageDTO.getId());
                            }
                            return filesRepository.findFirstByFilePath(imageDTO.getPath());
                        }
                )
                        .collect(Collectors.toList())
        );
        return product;
    }
}
