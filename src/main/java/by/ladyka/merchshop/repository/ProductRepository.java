package by.ladyka.merchshop.repository;

import by.ladyka.club.entity.EventReportEntity;
import by.ladyka.merchshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(

            value = "WITH RECURSIVE category (parent_product_category_id) AS (\n" +
                    "  SELECT parent_product_category_id FROM merchshop_product_category WHERE id = :category_id \n" +
                    "  UNION ALL\n" +
                    "  SELECT c1.parent_product_category_id FROM merchshop_product_category c1 INNER JOIN category c2 ON c1.id = c2.parent_product_category_id\n" +
                    ")\n" +
                    "SELECT merchshop_product.* FROM ( SELECT category.parent_product_category_id FROM (SELECT :category_id as parent_product_category_id UNION SELECT * FROM category ) category) category JOIN merchshop_product ON category.parent_product_category_id = merchshop_product.product_category_id",

            countQuery = "WITH RECURSIVE category (parent_product_category_id) AS (\n" +
                    "  SELECT parent_product_category_id FROM merchshop_product_category WHERE id = :category_id\n" +
                    "  UNION ALL\n" +
                    "  SELECT c1.parent_product_category_id FROM merchshop_product_category c1 INNER JOIN category c2 ON c1.id = c2.parent_product_category_id\n" +
                    ")\n" +
                    "SELECT COUNT(merchshop_product.id) FROM ( SELECT category.parent_product_category_id FROM (SELECT :category_id as parent_product_category_id UNION SELECT * FROM category ) category) category JOIN merchshop_product ON category.parent_product_category_id = merchshop_product.product_category_id;",

            nativeQuery = true

    )
    Page<Product> findProductByCategory(@Param("category_id") long categoryId, Pageable pageable);
}
