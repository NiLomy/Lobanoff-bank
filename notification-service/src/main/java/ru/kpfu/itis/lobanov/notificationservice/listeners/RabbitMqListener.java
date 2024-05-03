package ru.kpfu.itis.lobanov.notificationservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.dtos.EmailDto;
import ru.kpfu.itis.lobanov.notificationservice.services.EmailSenderService;

@Component
@RequiredArgsConstructor
public class RabbitMqListener {
    private final EmailSenderService emailSenderService;

    @RabbitListener(queues = "${spring.rabbitmq.template.email.queue}")
    public void receiveEmailMessage(EmailDto emailDto) {
        emailSenderService.sendVerificationCode(emailDto);
    }
}
