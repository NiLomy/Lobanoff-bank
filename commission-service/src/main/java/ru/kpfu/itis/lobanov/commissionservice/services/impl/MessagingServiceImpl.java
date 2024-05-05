package ru.kpfu.itis.lobanov.commissionservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;
import ru.kpfu.itis.lobanov.commissionservice.services.MessagingService;

@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final RabbitTemplate template;

    @Value("${spring.rabbitmq.exchange}")
    String exchange;
    @Value("${spring.rabbitmq.template.transaction.cashback.routing.key}")
    String transactionCashbackRoutingKey;

    @Override
    public void sendTransactionToChargeCashback(@NonNull Transaction transaction) {
        template.convertAndSend(exchange, transactionCashbackRoutingKey, transaction);
    }
}
