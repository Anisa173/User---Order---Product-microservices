package com.spring.notification;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;

@Configuration
public class RabbitMQConfiguration {
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue getQueue() {
        return (Queue) QueueBuilder.durable().build();
    }

    @Bean
    public TopicExchange getExchange() {
        return ExchangeBuilder.topicExchange(exchangeName)
                .durable(true).build();
    }

    public Binding getBinding() {
        return BindingBuilder
                .bind((org.springframework.amqp.core.Queue) getQueue())
                .to(getExchange())
                .with(routingKey);
    }


    @Bean
    public Jackson2JsonMessageConverter getMessageConverter() {

        return new Jackson2JsonMessageConverter();
    }


    }




