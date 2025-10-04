package com.microservice.order.controller;

import com.microservice.order.dto.CartItemRequest;
import com.microservice.order.model.CartItem;
import com.microservice.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cartitem/")
public class CartItemController {
    private final CartItemService cartitemService;

    @PostMapping
    public ResponseEntity<Void> addToCartItem(@RequestHeader("X-User-ID") String UserId, @RequestBody CartItemRequest request) {
        cartitemService.addToCartItem(UserId, request);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping
    public boolean removeFromCartItem(@RequestHeader("X-USER-ID") String userId, @PathVariable Long productId) {
        boolean deleted = cartitemService.removeFomCartItem(userId, productId);
        return deleted ? ResponseEntity.noContent().build().hasBody() : ResponseEntity.notFound().build().hasBody();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> fetchItems(@RequestHeader("X-User-ID") String userId) {
        cartitemService.fetchItems(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

