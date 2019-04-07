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

            value = "WITH RECURSIVE category (parent_product_category_id, nesting_level) AS (\n" +
                    "  SELECT parent_product_category_id, 1 FROM merchshop_product_category WHERE id = :category_id\n" +
                    "  UNION ALL\n" +
                    "  SELECT c1.parent_product_category_id, c2.nesting_level + 1 FROM merchshop_product_category c1 INNER JOIN category c2 ON c1.id = c2.parent_product_category_id\n" +
                    ")\n" +
                    "SELECT merchshop_product.* FROM ( SELECT category.parent_product_category_id, category.nesting_level FROM (SELECT :category_id as parent_product_category_id, 0 as nesting_level UNION SELECT * FROM category ) category) category JOIN merchshop_product ON category.parent_product_category_id = merchshop_product.product_category_id ORDER BY category.nesting_level ASC",

            countQuery = "WITH RECURSIVE category (parent_product_category_id, nesting_level) AS (\n" +
                    "  SELECT parent_product_category_id, 1 FROM merchshop_product_category WHERE id = :category_id\n" +
                    "  UNION ALL\n" +
                    "  SELECT c1.parent_product_category_id, c2.nesting_level + 1 FROM merchshop_product_category c1 INNER JOIN category c2 ON c1.id = c2.parent_product_category_id\n" +
                    ")\n" +
                    "SELECT COUNT(merchshop_product.id) FROM ( SELECT category.parent_product_category_id, category.nesting_level FROM (SELECT :category_id as parent_product_category_id, 0 as nesting_level UNION SELECT * FROM category ) category) category JOIN merchshop_product ON category.parent_product_category_id = merchshop_product.product_category_id ORDER BY category.nesting_level ASC",

            nativeQuery = true

    )
    Page<Product> findProductByCategory(@Param("category_id") long categoryId, Pageable pageable);


    @Query(

            value = "WITH RECURSIVE category (parent_product_category_id, nesting_level) AS (\n" +
                    "  SELECT parent_product_category_id, 1 FROM merchshop_product_category WHERE id = :category_id\n" +
                    "  UNION ALL\n" +
                    "  SELECT c1.parent_product_category_id, c2.nesting_level + 1 FROM merchshop_product_category c1 INNER JOIN category c2 ON c1.id = c2.parent_product_category_id\n" +
                    ")\n" +
                    "SELECT merchshop_product.* FROM ( SELECT category.parent_product_category_id, category.nesting_level FROM (SELECT :category_id as parent_product_category_id, 0 as nesting_level UNION SELECT * FROM category ) category) category JOIN merchshop_product ON category.parent_product_category_id = merchshop_product.product_category_id WHERE merchshop_product.visible = !FALSE ORDER BY category.nesting_level ASC",

            countQuery = "WITH RECURSIVE category (parent_product_category_id, nesting_level) AS (\n" +
                    "  SELECT parent_product_category_id, 1 FROM merchshop_product_category WHERE id = :category_id\n" +
                    "  UNION ALL\n" +
                    "  SELECT c1.parent_product_category_id, c2.nesting_level + 1 FROM merchshop_product_category c1 INNER JOIN category c2 ON c1.id = c2.parent_product_category_id\n" +
                    ")\n" +
                    "SELECT COUNT(merchshop_product.id) FROM ( SELECT category.parent_product_category_id, category.nesting_level FROM (SELECT :category_id as parent_product_category_id, 0 as nesting_level UNION SELECT * FROM category ) category) category JOIN merchshop_product ON category.parent_product_category_id = merchshop_product.product_category_id WHERE merchshop_product.visible = !FALSE ORDER BY category.nesting_level ASC",

            nativeQuery = true

    )
    Page<Product> findProductByCategoryAndIsVisible(@Param("category_id") long categoryId, Pageable pageable);
}
