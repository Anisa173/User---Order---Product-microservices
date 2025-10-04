package com.microservice.product.controller;


import com.microservice.product.dto.ProductRequest;
import com.microservice.product.dto.ProductResponse;
import com.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<ProductResponse> createNewProduct(@RequestBody ProductRequest productRequest) throws Exception {
        return new ResponseEntity<ProductResponse>(productService.createNewProduct(productRequest), HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() throws Exception {
        return ResponseEntity.ok(productService.getProducts());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(Long id) throws Exception {
        productService.deleteProduct(id);
    // boolean deleted = productService.deleteProduct(id);
    return new ResponseEntity<Void>(HttpStatus.OK);
    // return deleted ? ResponseEntity.notContent().build():ResponseEntity.notFound().build();
    }

}
