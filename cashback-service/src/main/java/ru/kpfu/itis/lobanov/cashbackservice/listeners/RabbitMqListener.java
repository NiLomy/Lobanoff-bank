package ru.kpfu.itis.lobanov.cashbackservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.cashbackservice.entities.Transaction;
import ru.kpfu.itis.lobanov.cashbackservice.services.CashbackChargeService;

@Component
@RequiredArgsConstructor
public class RabbitMqListener {
    private final CashbackChargeService cashbackChargeService;

    @RabbitListener(queues = "${spring.rabbitmq.template.transaction.cashback.queue}")
    public void receiveTransactionMessage(Transaction transaction) {
        cashbackChargeService.chargeCashback(transaction);
    }
}
