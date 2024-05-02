package ru.kpfu.itis.lobanov.notificationservice.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.dtos.EmailDto;

public interface EmailSenderService {
    void sendVerificationCode(@NonNull EmailDto emailDto);
}
