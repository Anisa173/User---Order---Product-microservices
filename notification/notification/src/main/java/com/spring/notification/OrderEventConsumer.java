package com.spring.notification;

import com.spring.notification.payload.OrderCreatedEvent;
import com.spring.notification.payload.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderEventConsumer {
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOderEvent(OrderCreatedEvent orderEvent) {
        System.out.println("The event is handled " + orderEvent);
        Long orderId = orderEvent.getOrderId();
        OrderStatus orderStatus = orderEvent.getStatus();
        System.out.println("OrderId: " + orderId);
        System.out.println("Order status is: " + orderStatus);

    }

}
