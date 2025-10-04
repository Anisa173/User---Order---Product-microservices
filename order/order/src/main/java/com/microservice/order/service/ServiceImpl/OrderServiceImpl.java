package com.microservice.order.service.ServiceImpl;


import com.microservice.order.dto.OrderItemDto;
import com.microservice.order.dto.OrderResponse;
import com.microservice.order.model.CartItem;
import com.microservice.order.model.Order;
import com.microservice.order.model.OrderItem;
import com.microservice.order.model.OrderStatus;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.service.CartItemService;
import com.microservice.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartItemService cService;
    //private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(CartItemService cService ,OrderRepository orderRepository) {
        this.cService = cService;
        //this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderResponse> createOrder(String userId) throws Exception {
        //Validate for cartItems
        List<CartItem> cartItemList = cService.getCartByuserId(userId);
        if (cartItemList.isEmpty()) {
            return Optional.empty();
        }

        //Validate for user
       // Optional<User> userOpt = userRepository.findById(Integer.valueOf(userId));
        //User user = null;
        //if (userOpt.isPresent()) {
          //  user = userOpt.get();
        //}

        //Calculate totalPrice of the order
        BigDecimal totalPrice = cartItemList.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //Create the order
        Order order = new Order();
        order.setUser_Id(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItemList.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct_id(),
                        item.getQuantity(),
                        item.getPrice(),
                        order.getId()

                )).collect(toList());
        order.setOrderItems(orderItems);
        Order orderi = orderRepository.save(order);
        //Clear the CartItem
        cService.clearCart(userId);

        return Optional.of(mapToOrderResponse(orderi));
    }

    private OrderResponse mapToOrderResponse(Order orderi) {
    return new OrderResponse(
            orderi.getId(),
            orderi.getUser_Id(),
            orderi.getTotalAmount(),
            orderi.getOrderItems().stream()
                    .map(orderitem->new OrderItemDto(
                            orderitem.getId(),

                            orderitem.getProduct_id(),
                            orderitem.getQuantity(),
                            orderitem.getPrice(),
                            orderitem.getPrice().multiply(new BigDecimal(orderitem.getQuantity()))

                    )).toList(),
            orderi.getStatus(),
            orderi.getCreatedAt(),
            orderi.getUpdatedAt()
            );


    }
}
