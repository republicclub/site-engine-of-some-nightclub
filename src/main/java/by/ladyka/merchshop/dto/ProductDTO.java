package by.ladyka.merchshop.dto;

import by.ladyka.club.entity.FileEntity;
import by.ladyka.merchshop.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private BigDecimal cost;
    private String name;
    private String description;
    private String size;
    private ProductCategoryDTO productCategory;
    private List<ImageDTO> images;
}
