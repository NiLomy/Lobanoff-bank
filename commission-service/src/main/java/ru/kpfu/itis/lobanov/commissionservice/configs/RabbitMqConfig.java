package ru.kpfu.itis.lobanov.commissionservice.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.username}")
    String username;
    @Value("${spring.rabbitmq.password}")
    String password;
    @Value("${spring.rabbitmq.exchange}")
    String exchange;
    @Value("${spring.rabbitmq.template.transaction.commission.queue}")
    String transactionCommissionQueue;
    @Value("${spring.rabbitmq.template.transaction.commission.routing.key}")
    String transactionCommissionRoutingKey;
    @Value("${spring.rabbitmq.template.transaction.cashback.queue}")
    String transactionCashbackQueue;
    @Value("${spring.rabbitmq.template.transaction.cashback.routing.key}")
    String transactionCashbackRoutingKey;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue transactionCommissionQueue() {
        return new Queue(transactionCommissionQueue);
    }

    @Bean
    public Binding transactionComissionBinding() {
        return BindingBuilder
                .bind(transactionCommissionQueue())
                .to(directExchange())
                .with(transactionCommissionRoutingKey);
    }

    @Bean
    public Queue transactionCashbackQueue() {
        return new Queue(transactionCashbackQueue);
    }

    @Bean
    public Binding transactionCashbackBinding() {
        return BindingBuilder
                .bind(transactionCashbackQueue())
                .to(directExchange())
                .with(transactionCashbackRoutingKey);
    }
}