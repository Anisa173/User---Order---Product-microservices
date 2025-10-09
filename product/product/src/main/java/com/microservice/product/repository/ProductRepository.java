package com.microservice.product.repository;


import com.microservice.product.dto.ProductResponse;
import com.microservice.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findActiveByActiveTrue();
    Optional<Product> findByIdAndByActiveTrue(Long id);
}
