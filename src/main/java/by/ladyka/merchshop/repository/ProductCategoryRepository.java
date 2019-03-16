package by.ladyka.merchshop.repository;

import by.ladyka.club.entity.EventReportEntity;
import by.ladyka.merchshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory, Long> { }