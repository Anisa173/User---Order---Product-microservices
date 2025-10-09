package com.microservice.order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProducthttpInterfaceConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }


    @Bean
    public ProductHttpClientService providerHttpClientService(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl("http://product-service").build();
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        ProductHttpClientService service = httpServiceProxyFactory.createClient(ProductHttpClientService.class);

        return service;
    }


}
