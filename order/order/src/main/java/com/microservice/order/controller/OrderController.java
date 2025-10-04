package com.microservice.order.controller;

import com.microservice.order.dto.OrderResponse;
import com.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order/")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Optional<OrderResponse>> createOrder(@RequestHeader("X-User-Id") String userId) throws Exception {
        Optional<OrderResponse> orderResponse = orderService.createOrder(userId);
        return new ResponseEntity<Optional<OrderResponse>>(orderResponse, HttpStatus.CREATED);
    }
}
