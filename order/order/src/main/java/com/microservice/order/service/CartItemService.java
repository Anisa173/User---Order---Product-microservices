package com.microservice.order.service;


import com.microservice.order.dto.CartItemRequest;
import com.microservice.order.model.CartItem;

import java.util.List;

public interface CartItemService {
    public Boolean addToCartItem(String userId, CartItemRequest request);

  public  boolean removeFomCartItem(String userId, Long productId);

  public List<CartItem> fetchItems(String userId);

   // List<CartItem> getCartByuserId(String userId);

    public void clearCart(String userId);

    List<CartItem> getCartByuserId(String userId);
}
