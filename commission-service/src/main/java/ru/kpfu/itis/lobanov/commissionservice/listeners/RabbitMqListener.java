package ru.kpfu.itis.lobanov.commissionservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;
import ru.kpfu.itis.lobanov.commissionservice.services.CommissionChargeService;

@Component
@RequiredArgsConstructor
public class RabbitMqListener {
    private final CommissionChargeService commissionChargeService;

    @RabbitListener(queues = "${spring.rabbitmq.template.transaction.commission.queue}")
    public void receiveTransactionMessage(Transaction transaction) {
        commissionChargeService.chargeCommission(transaction);
    }
}
