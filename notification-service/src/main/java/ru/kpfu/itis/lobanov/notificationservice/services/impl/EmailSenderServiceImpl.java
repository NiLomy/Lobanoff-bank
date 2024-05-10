package ru.kpfu.itis.lobanov.notificationservice.services.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.kpfu.itis.lobanov.dtos.EmailDto;
import ru.kpfu.itis.lobanov.notificationservice.configs.MailContentConfig;
import ru.kpfu.itis.lobanov.notificationservice.services.EmailSenderService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final MailContentConfig mailContentConfig;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void sendVerificationCode(@NonNull EmailDto emailDto) {
        validateDataForEmail(emailDto.getEmail(), emailDto.getReceiverName(), emailDto.getUrl());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("test.ftlh");
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", emailDto.getReceiverName());
            templateModel.put("url", emailDto.getUrl());
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
            helper.setFrom(mailContentConfig.getFrom(), mailContentConfig.getSender());
            helper.setTo(emailDto.getEmail());
            helper.setSubject(mailContentConfig.getSubject());
            helper.setText(htmlBody, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateDataForEmail(@NonNull String mail, @NonNull String name, @NonNull String code) {
        if (mail.isBlank()) throw new IllegalArgumentException("Email should be provided");
        if (name.isBlank()) throw new IllegalArgumentException("Name should be provided");
        if (code.isBlank()) throw new IllegalArgumentException("Verification code should be provided");
    }
}
