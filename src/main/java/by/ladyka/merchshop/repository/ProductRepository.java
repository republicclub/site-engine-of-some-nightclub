package by.ladyka.merchshop.repository;

import by.ladyka.club.entity.EventReportEntity;
import by.ladyka.merchshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
