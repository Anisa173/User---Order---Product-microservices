package com.microservice.order.config;

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
    public AmqpAdmin amqAdmin(ConnectionFactory factory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin((org.springframework.amqp.rabbit.connection.ConnectionFactory) factory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public Jackson2JsonMessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        rabbitTemplate.setExchange(exchangeName);
        return rabbitTemplate;

    }


}

