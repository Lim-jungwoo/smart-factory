package com.example.devicemonitoringserver.domain.product.repository;

import com.example.devicemonitoringserver.domain.product.entity.Product;
import com.example.devicemonitoringserver.domain.product.entity.ProductPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPartRepository extends JpaRepository<ProductPart, Long> {
    List<ProductPart> findAllByProduct(Product product);
}
