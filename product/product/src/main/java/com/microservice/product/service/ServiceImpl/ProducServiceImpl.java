package com.microservice.product.service.ServiceImpl;


import com.microservice.product.dto.ProductRequest;
import com.microservice.product.dto.ProductResponse;
import com.microservice.product.model.Product;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ProducServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createNewProduct(ProductRequest productRequest) throws Exception {
        Product product = new Product();
        ProductRequest prodRequest = new ProductRequest();
        product = mapProdRequestTOEntity(prodRequest);
        Product saveProduct = productRepository.save(product);

        return mapTOProductResponse(saveProduct);
    }

    private ProductResponse mapTOProductResponse(Product saveProduct) {
        ProductResponse prodResponse = new ProductResponse();
        prodResponse.setId(saveProduct.getId());
        prodResponse.setName(saveProduct.getName());
        prodResponse.setDescription(saveProduct.getDescription());
        prodResponse.setPrice(saveProduct.getPrice());
        prodResponse.setCategory(saveProduct.getCategory());
        prodResponse.setStockQuantity(saveProduct.getStockQuantity());
        prodResponse.setImageURL(saveProduct.getImageURL());
        prodResponse.setActive(saveProduct.getActive());
        return prodResponse;
    }

    private Product mapProdRequestTOEntity(ProductRequest prodRequest) {
        Product product = new Product();
        product.setName(prodRequest.getName());
        product.setDescription(prodRequest.getDescription());
        product.setPrice(prodRequest.getPrice());
        product.setStockQuantity(prodRequest.getStockQuantity());
        product.setCategory(prodRequest.getCategory());
        product.setImageURL(prodRequest.getImageURL());
        return product;
    }


    @Override
    public ProductResponse updateProduct(ProductRequest productRequest, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Product prod = updateProductFromProductRequest(existingProduct, productRequest);
                    Product product = productRepository.save(existingProduct);
                    ProductResponse productResponse = mapFromProductToProductResponse(product);
                    return productResponse;
                }).orElseThrow(() -> new RuntimeException("Product not found" + id));
    }


    private ProductResponse mapFromProductToProductResponse(Product product) {
        ProductResponse prodResponse = new ProductResponse();
        prodResponse.setId(product.getId());
        prodResponse.setName(product.getName());
        prodResponse.setDescription(product.getDescription());
        prodResponse.setPrice(product.getPrice());
        prodResponse.setStockQuantity(product.getStockQuantity());
        prodResponse.setCategory(product.getCategory());
        prodResponse.setImageURL(product.getImageURL());
        prodResponse.setActive(product.getActive());
        return prodResponse;
    }

    private Product updateProductFromProductRequest(Product existingProduct, ProductRequest productRequest) {
        //existingProduct.setId(productRequest.getId());
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setCategory(productRequest.getCategory());
        existingProduct.setImageURL(productRequest.getImageURL());
        return existingProduct;
    }

    @Override
    public ProductResponse getProductById(Long id) throws Exception {
        return productRepository.findById(id).map(c -> mapTOProductResponse(c))
                .orElseThrow(() -> new RuntimeException("Product is not found"));
    }

    @Override
    public List<ProductResponse> getProducts() throws Exception {

        return productRepository.findActiveByActiveTrue()
                .stream()
                .map(c->mapTOProductResponse(c))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteProduct(Long id) throws Exception {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }


}
