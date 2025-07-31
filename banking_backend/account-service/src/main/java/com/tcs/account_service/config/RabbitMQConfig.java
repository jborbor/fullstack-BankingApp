package com.tcs.account_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLIENTE_REQUEST_QUEUE = "cliente.request.queue";
    public static final String CLIENTE_RESPONSE_QUEUE = "cliente.response.queue";
    public static final String CLIENTE_EXCHANGE = "cliente.exchange";
    public static final String REQUEST_ROUTING_KEY = "cliente.request";
    public static final String RESPONSE_ROUTING_KEY = "cliente.response";

    // Configurar Jackson para manejar objetos JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para LocalDateTime, etc.
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // Configurar RabbitTemplate con el converter JSON
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    // Configurar listener container factory con el converter JSON
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
//            ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(jsonMessageConverter());
//        return factory;
//    }

    @Bean
    public TopicExchange clienteExchange() {
        return new TopicExchange(CLIENTE_EXCHANGE);
    }

    @Bean
    public Queue clienteRequestQueue() {
        return QueueBuilder.durable(CLIENTE_REQUEST_QUEUE).build();
    }

    @Bean
    public Queue clienteResponseQueue() {
        return QueueBuilder.durable(CLIENTE_RESPONSE_QUEUE).build();
    }

    @Bean
    public Binding requestBinding() {
        return BindingBuilder
                .bind(clienteRequestQueue())
                .to(clienteExchange())
                .with(REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding responseBinding() {
        return BindingBuilder
                .bind(clienteResponseQueue())
                .to(clienteExchange())
                .with(RESPONSE_ROUTING_KEY);
    }

}
