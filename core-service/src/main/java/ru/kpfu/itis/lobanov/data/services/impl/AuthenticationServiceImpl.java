package ru.kpfu.itis.lobanov.data.services.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.kpfu.itis.lobanov.configs.MailContentConfig;
import ru.kpfu.itis.lobanov.data.entities.RefreshToken;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.RefreshTokenRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.AuthenticationService;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.forms.LoginForm;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.responses.TokenResponse;
import ru.kpfu.itis.lobanov.utils.JwtProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JavaMailSender javaMailSender;
    private final MailContentConfig mailContentConfig;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final Mapper<User, UserDto> userMapper;

    @Override
    public TokenResponse register(@NonNull RegistrationForm registrationForm) {
        validateRegistrationForm(registrationForm);

        String code = RandomString.make(128);
        User user = User.builder()
                .name(registrationForm.getName())
                .lastname(registrationForm.getLastname())
                .patronymic(registrationForm.getPatronymic())
                .email(registrationForm.getEmail())
                .password(passwordEncoder.encode(registrationForm.getPassword()))
                .verificationCode(code)
                .role(Role.USER)
                .state(State.BANNED)
                .build();

        sendVerificationCode(registrationForm.getEmail(), registrationForm.getName(), code);

        user = userRepository.save(user);
        return generateToken(user);
    }

    @Override
    public void sendVerificationCode(@NonNull String mail, @NonNull String name, @NonNull String code) {
        validateDataForEmail(mail, name, code);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("test.ftlh");
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", name);
            templateModel.put("code", code);
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
            helper.setFrom(mailContentConfig.getFrom(), mailContentConfig.getSender());
            helper.setTo(mail);
            helper.setSubject(mailContentConfig.getSubject());
            helper.setText(htmlBody, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TokenResponse verify(@NonNull String code) {
        if (code.isBlank()) throw new IllegalArgumentException("Verification code should be provided");

        Optional<User> optionalUser = userRepository.findByVerificationCode(code);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVerificationCode(null);
            user.setState(State.ACTIVE);
            user = userRepository.save(user);

            return generateToken(user);
        }
        return null;
    }

    @Override
    public TokenResponse login(@NonNull LoginForm loginForm) {
        validateLoginForm(loginForm);

        User user = userRepository.findByEmail(loginForm.getEmail()).orElseThrow(IllegalArgumentException::new);

        if (passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            return generateToken(user);
        } else throw new IllegalArgumentException("Wrong email or password.");
    }

    @Override
    public TokenResponse getAccessToken(@NonNull String refreshToken) {
        if (refreshToken.isBlank()) throw new IllegalArgumentException("Refresh token should be provided");

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            Long id = Long.parseLong(claims.getSubject());
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                Optional<RefreshToken> saveRefreshToken = refreshTokenRepository.findByUserId(id);
                if (saveRefreshToken.isPresent() && saveRefreshToken.get().getToken().equals(refreshToken)) {
                    String accessToken = jwtProvider.generateAccessToken(user);
                    return new TokenResponse(accessToken, null);
                }
            }
        }
        return new TokenResponse(null, null);
    }

    @Override
    public TokenResponse refreshTokens(@NonNull String refreshToken) {
        if (refreshToken.isBlank()) throw new IllegalArgumentException("Refresh token should be provided");

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            Long id = Long.parseLong(claims.getSubject());
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                Optional<RefreshToken> saveRefreshToken = refreshTokenRepository.findByUserId(id);
                if (saveRefreshToken.isPresent() && saveRefreshToken.get().getToken().equals(refreshToken)) {
                    return generateToken(user);
                }
            }
        }
        throw new IllegalArgumentException("Invalid JWT");
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
            return null;
        User user = (User) authentication.getPrincipal();
        return userMapper.toResponse(user);
    }

    private TokenResponse generateToken(@NonNull User user) {
        String refreshToken = jwtProvider.generateRefreshToken(user);
        String accessToken = jwtProvider.generateAccessToken(user);

        if (refreshTokenRepository.existsByUserId(user.getId())) {
            refreshTokenRepository.updateToken(user.getId(), refreshToken);
        } else {
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .user(user)
                            .token(refreshToken)
                            .build()
            );
        }
        return new TokenResponse(accessToken, refreshToken);
    }

    private void validateRegistrationForm(@NonNull RegistrationForm registrationForm) {
        String name = registrationForm.getName();
        String lastname = registrationForm.getLastname();
        String patronymic = registrationForm.getPatronymic();
        String email = registrationForm.getEmail();
        String password = registrationForm.getPassword();
        String confirmPassword = registrationForm.getConfirmPassword();

        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name should be provided");
        if (lastname == null || lastname.isBlank()) throw new IllegalArgumentException("Lastname should be provided");
        if (patronymic == null || patronymic.isBlank())
            throw new IllegalArgumentException("Patronymic should be provided");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email should be provided");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password should be provided");
        if (confirmPassword == null || confirmPassword.isBlank())
            throw new IllegalArgumentException("Confirm password should be provided");

        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("This email is already exists.");
        if (password.equals(registrationForm.getConfirmPassword()))
            throw new IllegalArgumentException("Passwords don't match.");
    }

    private void validateLoginForm(@NonNull LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email should be provided");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password should be provided");
    }

    private void validateDataForEmail(@NonNull String mail, @NonNull String name, @NonNull String code) {
        if (mail.isBlank()) throw new IllegalArgumentException("Email should be provided");
        if (name.isBlank()) throw new IllegalArgumentException("Name should be provided");
        if (code.isBlank()) throw new IllegalArgumentException("Verification code should be provided");
    }
}
