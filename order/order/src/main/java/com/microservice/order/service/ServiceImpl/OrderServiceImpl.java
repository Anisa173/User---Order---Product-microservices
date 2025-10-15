package com.microservice.order.service.ServiceImpl;


import com.microservice.order.dto.OrderCreatedEvent;
import com.microservice.order.dto.OrderItemDto;
import com.microservice.order.dto.OrderResponse;
import com.microservice.order.model.CartItem;
import com.microservice.order.model.Order;
import com.microservice.order.model.OrderItem;
import com.microservice.order.model.OrderStatus;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.service.CartItemService;
import com.microservice.order.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartItemService cService;
    //private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderServiceImpl(CartItemService cService, OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.cService = cService;
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
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
        BigDecimal totalPrice = cartItemList.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        //Create the order
        Order order = new Order();
        order.setUser_Id(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItemList.stream().map(item ->
                new OrderItem(
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
        //Publish order created event
        OrderCreatedEvent event = new OrderCreatedEvent(
                orderi.getId(),
                orderi.getUser_Id(),
                orderi.getStatus(),
                (BigDecimal) mapToOrderItemsDto(orderi.getOrderItems()),
                (List<OrderItemDto>) orderi.getTotalAmount(),
                orderi.getCreatedAt()
        );
        rabbitTemplate.convertAndSend("order.exchange", "order.tracking"
                ,event);
        rabbitTemplate.convertAndSend("order.exchange", "order.tracking"
                , Map.of("orderId", orderi.getId(), "status", "CREATED"));

        return Optional.of(mapToOrderResponse(orderi));
    }

    private OrderResponse mapToOrderResponse(Order orderi) {
        return new OrderResponse(orderi.getId(),
                orderi.getUser_Id(),
                orderi.getTotalAmount(),
                orderi.getOrderItems()
                        .stream()
                        .map(orderitem -> new OrderItemDto(
                                orderitem.getId(),
                                Math.toIntExact(orderitem.getProduct_id()),
                                orderitem.getQuantity(),
                                orderitem.getPrice(),
                                Math.toIntExact(orderitem.getOrder_id()),
                                orderitem.getPrice()
                                        .multiply(new BigDecimal(orderitem.getQuantity()))

                        )).toList(),
                orderi.getStatus(),
                orderi.getCreatedAt(),
                orderi.getUpdatedAt());


    }

    private List<OrderItemDto> mapToOrderItemsDto(List<OrderItem> orderItems) {
        return (List<OrderItemDto>) orderItems.stream().map(
                i -> new OrderItemDto(
                        i.getId(),
                        Math.toIntExact(i.getProduct_id()),
                        i.getQuantity(),
                        i.getPrice()
                        , Math.toIntExact(i.getOrder_id()),
                        i.getPrice().multiply(new BigDecimal(i.getQuantity()))
                )
        ).collect(Collectors.toList());
    }

}
