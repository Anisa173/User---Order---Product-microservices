package com.microservice.order.dto;

import com.microservice.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreatedEvent {

    private Long orderId;
    private String userId;
    private OrderStatus status;
    private BigDecimal amount;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;

}
