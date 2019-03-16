package by.ladyka.merchshop.entity;

import by.ladyka.club.entity.AbstractEntity;
import by.ladyka.club.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchshop_product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends AbstractEntity {
    private BigDecimal cost;
    private String name;
    @Lob
    private String description;
    private String size;
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;
    @ManyToMany
    @JoinTable(name = "merchshop_product_images",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private List<FileEntity> images;
}
