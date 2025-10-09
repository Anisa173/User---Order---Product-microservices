package com.microservice.order.clients;

import com.microservice.order.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductHttpClientService {

    @GetExchange("/api/products/{id}")
    public ProductResponse getProductDetails(@PathVariable String id);

}
