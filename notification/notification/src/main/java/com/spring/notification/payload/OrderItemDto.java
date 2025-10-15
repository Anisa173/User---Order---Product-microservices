package com.spring.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItemDto {

    private Long id;
    private Integer product_id;
    private Integer quantity;
    private BigDecimal price;
    private Integer order_id;
    private BigDecimal Total_price;


   
}
