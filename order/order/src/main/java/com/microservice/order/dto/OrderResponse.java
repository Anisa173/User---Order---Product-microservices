package com.microservice.order.dto;


import com.microservice.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {
    private Long id;
    private String userId;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}

