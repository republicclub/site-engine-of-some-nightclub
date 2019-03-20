package by.ladyka.merchshop.service;

import by.ladyka.merchshop.dto.ProductDTO;
import by.ladyka.merchshop.entity.Product;
import by.ladyka.merchshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConverterProductService converterProductService;

    public ProductDTO save(ProductDTO productDTO) {
        Product product;
        if(productDTO.getId() != null){
            product = productRepository.getOne(productDTO.getId());
        }else{
            product = new Product();
        }
        product = converterProductService.toEntity(productDTO, product);
        productRepository.save(product);
        return converterProductService.toDto(product);
    }

    public ProductDTO delete(ProductDTO productDTO) {
        Product product = productRepository.getOne(productDTO.getId());
        product.setVisible(false);
        productRepository.save(product);
        return productDTO;
    }

    public Page<ProductDTO> getByCategoryId(Integer page, Integer size, Long categoryId) {
        return productRepository.findProductByCategory(categoryId, PageRequest.of(page, size)).map(converterProductService::toDto);
    }

    public Page<ProductDTO> getByCategoryIdAndIsVisible(Integer page, Integer size, Long categoryId) {
        return productRepository.findProductByCategoryAndIsVisible(categoryId, PageRequest.of(page, size)).map(converterProductService::toDto);
    }
}
