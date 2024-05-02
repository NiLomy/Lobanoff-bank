package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.entities.Transaction;
import ru.kpfu.itis.lobanov.data.services.MessagingService;
import ru.kpfu.itis.lobanov.dtos.EmailDto;

@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final RabbitTemplate template;

    @Value("${spring.rabbitmq.exchange}")
    String exchange;
    @Value("${spring.rabbitmq.template.transaction.routing.key}")
    String transactionRoutingKey;
    @Value("${spring.rabbitmq.template.email.routing.key}")
    String emailRoutingKey;

    @Override
    public void sendTransactionToChargeCommission(@NonNull Transaction transaction) {
        template.convertAndSend(transactionRoutingKey, transaction);
    }

    @Override
    public void sendEmail(@NonNull String mail, @NonNull String name, @NonNull String code) {
        EmailDto emailDto = EmailDto.builder()
                        .email(mail)
                        .receiverName(name)
                        .code(code)
                        .build();
        template.convertAndSend(exchange, emailRoutingKey, emailDto);
    }
}
