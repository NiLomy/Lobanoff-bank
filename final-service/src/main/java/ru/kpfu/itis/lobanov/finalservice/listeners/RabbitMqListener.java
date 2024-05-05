package ru.kpfu.itis.lobanov.finalservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.finalservice.entities.Transaction;
import ru.kpfu.itis.lobanov.finalservice.services.TransactionProcessingService;

@Component
@RequiredArgsConstructor
public class RabbitMqListener {
    private final TransactionProcessingService transactionProcessingService;

    @RabbitListener(queues = "${spring.rabbitmq.template.transaction.final.queue}")
    public void receiveTransactionMessage(Transaction transaction) {
        transactionProcessingService.process(transaction);
    }
}
