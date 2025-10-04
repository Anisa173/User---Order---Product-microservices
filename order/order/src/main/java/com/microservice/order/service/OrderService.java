package com.microservice.order.service;



import com.microservice.order.dto.OrderResponse;

import java.util.Optional;

public interface OrderService {
public Optional<OrderResponse> createOrder(String userId) throws Exception;

}
