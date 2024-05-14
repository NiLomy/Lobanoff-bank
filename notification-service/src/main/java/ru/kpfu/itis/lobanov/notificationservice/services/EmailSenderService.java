package ru.kpfu.itis.lobanov.notificationservice.services;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.EmailDto;

import static ru.kpfu.itis.lobanov.notificationservice.utils.ValidationMessages.MESSAGE_NOT_NULL;

@Validated
public interface EmailSenderService {
    void sendVerificationCode(
            @NotNull(message = MESSAGE_NOT_NULL)
            EmailDto emailDto
    );
}
