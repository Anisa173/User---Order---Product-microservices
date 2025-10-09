package com.microservice.order.service.ServiceImpl;


import com.microservice.order.clients.ProductHttpClientService;
import com.microservice.order.clients.UserHttpServiceClient;
import com.microservice.order.dto.CartItemRequest;
import com.microservice.order.dto.ProductResponse;
import com.microservice.order.model.CartItem;
import com.microservice.order.repository.CartRepository;
import com.microservice.order.service.CartItemService;
import com.microservice.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemServiceIMPL implements CartItemService {

    private final CartRepository cartitemrepo;
    //private final UserRepository userRepo;
    //private final ProductRepository productRepo;
    private final ProductHttpClientService productClient;
    private final UserHttpServiceClient userClient;

    @Override
    public Boolean addToCartItem(String userId, CartItemRequest request) {
        //Look for product
        ProductResponse response = productClient
                .getProductDetails(String.valueOf(request.getProductById()));
        if ((response == null) || (response.getStockQuantity() < request.getQuantity())) {
            return false;
        }
        //   Optional<Product> productOpt = productRepo.findById(request.getProductById());
        // Optional<User> userOptional = null;
        //  if (productOpt.isEmpty()) {
        //    return false;
        // } else if (productOpt.isPresent()) {
        //   Product product = productOpt.get();
        // if (product.getStockQuantity() < request.getQuantity()) {
        //    return false;
        //  }
        UserResponse userResponse = userClient.getUserDetails(userId);
        if (userResponse == null) {
            return false;
        }

        //userOptional = userRepo.findById(Integer.valueOf(userId));
        //if (userOptional.isEmpty()) {
        //    return false;
        //} else if (userOptional.isPresent()) {
        //  User user = userOptional.get();

        CartItem existingCartItem = cartitemrepo.findByUserAndProd(userId, String.valueOf(request.getProductById()));
        if (existingCartItem != null) {
            //update quantity
            existingCartItem.setQuantity((int) (existingCartItem.getQuantity() + request.getQuantity()));
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartitemrepo.save(existingCartItem);

        } else {
            //Create a new cartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setUser_id(Long.valueOf(userId));
            newCartItem.setProduct_id(request.getProductById());
            newCartItem.setQuantity(Math.toIntExact(request.getQuantity()));
            newCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartitemrepo.save(newCartItem);

        }
        return true;
    }


    @Override
    public boolean removeFomCartItem(String userId, Long productId) {
        // Optional<Product> productOpt = productRepo.findById(productId);
        //Optional<User> userOptional = userRepo.findById(Integer.valueOf(userId));
        CartItem cartItem = cartitemrepo.findByUserAndProd(userId, String.valueOf(productId));

        if (cartItem != null) {

            cartitemrepo.delete(cartItem);
            return true;
        }
        return false;
    }

    @Override
    public List<CartItem> fetchItems(String userId) {
        return cartitemrepo.findCartItemsByUser(userId);
    }

    @Override
    public void clearCart(String userId) {
        cartitemrepo.deleteByUser(userId);
    }

    @Override
    public List<CartItem> getCartByuserId(String userId) {
        return List.of();
    }


}




