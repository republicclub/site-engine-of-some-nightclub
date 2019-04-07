package by.ladyka.merchshop.dto;

import by.ladyka.merchshop.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Long parentCategoryId;
}
