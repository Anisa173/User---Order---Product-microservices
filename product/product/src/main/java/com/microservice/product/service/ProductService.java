package com.microservice.product.service;
import com.microservice.product.dto.ProductRequest;
import com.microservice.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    public ProductResponse createNewProduct(ProductRequest productRequest) throws Exception;

    public ProductResponse updateProduct(ProductRequest productRequest, Long id) throws Exception;

    public ProductResponse getProductById(Long id) throws Exception;

    public List<ProductResponse> getProducts() throws Exception;

    public Boolean deleteProduct(Long id) throws Exception;
}
