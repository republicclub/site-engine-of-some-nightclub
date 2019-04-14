package by.ladyka.merchshop.entity;

import by.ladyka.club.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchshop_product_category")
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory  extends AbstractEntity {
    private String name;
    @Lob
    private String description;
    @ManyToOne
    @JoinColumn(name = "parent_product_category_id")
    private ProductCategory parentProductCategory;
}
