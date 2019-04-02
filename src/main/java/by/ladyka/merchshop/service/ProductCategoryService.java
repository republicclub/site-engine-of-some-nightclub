package by.ladyka.merchshop.service;

import by.ladyka.merchshop.dto.ProductCategoryDTO;
import by.ladyka.merchshop.entity.Product;
import by.ladyka.merchshop.entity.ProductCategory;
import by.ladyka.merchshop.repository.ProductCategoryRepository;
import by.ladyka.merchshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {


    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ConverterProductCategoryService converterProductCategoryService;


    public ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory;
        if(productCategoryDTO.getId() != null){
            productCategory = productCategoryRepository.getOne(productCategoryDTO.getId());
        }else{
            productCategory = new ProductCategory();
        }
        productCategory = converterProductCategoryService.toEntity(productCategoryDTO, productCategory);
        productCategoryRepository.save(productCategory);
        return converterProductCategoryService.toDto(productCategory);
    }

    public ProductCategoryDTO delete(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryRepository.getOne(productCategoryDTO.getId());
        productCategory.setVisible(false);
        productCategoryRepository.save(productCategory);
        return productCategoryDTO;
    }

    public List<ProductCategoryDTO> getSubcategoriesByParentCategoryId(Long parentCategoryId) {
        return productCategoryRepository.findAllByParentProductCategory(productCategoryRepository.getOne(parentCategoryId)).stream().map(converterProductCategoryService::toDto).collect(Collectors.toList());
    }

    public List<ProductCategoryDTO> getSubcategoriesByParentCategoryIdAndIsVisible(Long parentCategoryId) {
        return productCategoryRepository.findAllByParentProductCategoryAndVisibleIsTrue(productCategoryRepository.getOne(parentCategoryId)).stream().map(converterProductCategoryService::toDto).collect(Collectors.toList());
    }
}
