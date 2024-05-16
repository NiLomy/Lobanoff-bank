package ru.kpfu.itis.lobanov.data.services.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.Passport;
import ru.kpfu.itis.lobanov.data.entities.RefreshToken;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.PassportRepository;
import ru.kpfu.itis.lobanov.data.repositories.RefreshTokenRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.AuthenticationService;
import ru.kpfu.itis.lobanov.data.services.MessagingService;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.forms.LoginForm;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.responses.TokenResponse;
import ru.kpfu.itis.lobanov.utils.DateProvider;
import ru.kpfu.itis.lobanov.utils.JwtProvider;

import java.util.Optional;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.ADMIN_EMAIL_PREFIX;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MessagingService messagingService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final DateProvider dateProvider;
    private final Mapper<User, UserDto> userMapper;

    @Override
    public TokenResponse register(RegistrationForm registrationForm) {
        validateRegistrationForm(registrationForm);

        String code = RandomString.make(128);
        Passport passport = Passport.builder()
                .name(registrationForm.getName())
                .lastname(registrationForm.getLastname())
                .patronymic(registrationForm.getPatronymic())
                .series(registrationForm.getSeries())
                .number(registrationForm.getNumber())
                .birthday(dateProvider.parseDate(registrationForm.getBirthday()))
                .gender(registrationForm.getGender())
                .departmentCode(registrationForm.getDepartmentCode())
                .issuedBy(registrationForm.getIssuedBy())
                .issuedDate(dateProvider.parseDate(registrationForm.getIssuedDate()))
                .address(registrationForm.getAddress())
                .build();

        passport = passportRepository.save(passport);

        User.UserBuilder userBuilder = User.builder()
                .passport(passport)
                .email(registrationForm.getEmail())
                .password(passwordEncoder.encode(registrationForm.getPassword()))
                .deleted(false);

        if (!registrationForm.getEmail().startsWith(ADMIN_EMAIL_PREFIX)) {
            userBuilder
                    .role(Role.USER)
                    .verificationCode(code)
                    .state(State.BANNED);

            messagingService.sendEmail(registrationForm.getEmail(), registrationForm.getName(), registrationForm.getUrl() + code);
        } else {
            userBuilder
                    .role(Role.ADMIN)
                    .state(State.ACTIVE);
        }

        User user = userRepository.save(userBuilder.build());
        return generateToken(user);
    }

    @Override
    public TokenResponse verify(String code) {
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
    public TokenResponse login(LoginForm loginForm) {
        validateLoginForm(loginForm);

        User user = userRepository.findByEmail(loginForm.getEmail()).orElseThrow(IllegalArgumentException::new);

        if (passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            return generateToken(user);
        } else throw new IllegalArgumentException("Wrong email or password.");
    }

    @Override
    public TokenResponse getAccessToken(String refreshToken) {
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
    public TokenResponse refreshTokens(String refreshToken) {
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

    private TokenResponse generateToken(User user) {
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

    private void validateRegistrationForm(RegistrationForm registrationForm) {
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
        if (!password.equals(registrationForm.getConfirmPassword()))
            throw new IllegalArgumentException("Passwords don't match.");
    }

    private void validateLoginForm(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email should be provided");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password should be provided");
    }
}
