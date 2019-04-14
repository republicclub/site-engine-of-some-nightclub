package by.ladyka.merchshop.repository;

import by.ladyka.club.entity.EventReportEntity;
import by.ladyka.merchshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByParentProductCategory(ProductCategory parentProductCategory);
    List<ProductCategory> findAllByParentProductCategoryAndVisibleIsTrue(ProductCategory parentProductCategory);
}
