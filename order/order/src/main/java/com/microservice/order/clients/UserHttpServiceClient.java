package com.microservice.order.clients;

import com.microservice.user.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserHttpServiceClient {

    @GetExchange("/api/users/{userId}")
    public UserResponse getUserDetails(@PathVariable String userId);

}

