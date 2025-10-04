package com.microservice.order.repository;


import com.microservice.order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> product(String productid);

    CartItem findByUserAndProd(String userId,String product_Id);

    CartItem deleteByUserAndProduct(String product_Id,String userId);

    List<CartItem> findCartItemsByUser(String userId);

    public void deleteByUser(String userId);
}
