package by.ladyka.merchshop.service;

import by.ladyka.merchshop.dto.ProductCategoryDTO;
import by.ladyka.merchshop.entity.ProductCategory;
import by.ladyka.merchshop.repository.ProductCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDTO toDto(ProductCategory productCategory) {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        BeanUtils.copyProperties(productCategory, productCategoryDTO);
        productCategoryDTO.setParentCategoryId(productCategory.getParentProductCategory().getId());
        return productCategoryDTO;
    }

    public ProductCategory toEntity(ProductCategoryDTO productCategoryDTO, ProductCategory productCategory) {
        BeanUtils.copyProperties(productCategoryDTO, productCategory);
        ProductCategory parentProductCategory = productCategoryRepository.getOne(productCategoryDTO.getParentCategoryId());
        productCategory.setParentProductCategory(parentProductCategory);
        return productCategory;
    }
}
