package ru.kpfu.itis.lobanov.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.configs.JwtSecretsConfig;
import ru.kpfu.itis.lobanov.data.entities.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.*;
import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.ACCESS_TOKEN_DURATION_MINUTES;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.REFRESH_TOKEN_DURATION_MONTHS;

@Component
@Validated
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtSecretsConfig jwtSecretsConfig;

    public String generateAccessToken(
            @NotNull(message = USER_NOT_NULL)
            User user
    ) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(ACCESS_TOKEN_DURATION_MINUTES)
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim(EMAIL_KEY, user.getEmail())
                .claim(AUTHORITY_KEY, user.getRole().name())
                .claim(ENABLED_KEY, user.isEnabled())
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretsConfig.getAccess())))
                .compact();
    }

    public String generateRefreshToken(
            @NotNull(message = USER_NOT_NULL)
            User user
    ) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMonths(REFRESH_TOKEN_DURATION_MONTHS)
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretsConfig.getRefresh())))
                .compact();
    }

    public boolean validateAccessToken(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String accessToken,
            @NotNull(message = EMAIL_NOT_NULL)
            @NotBlank(message = EMAIL_NOT_BLANK)
            String email
    ) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretsConfig.getAccess()));
        return validateToken(accessToken, secret) &&
                getClaims(accessToken, secret).get(EMAIL_KEY, String.class).equals(email);
    }

    public boolean validateRefreshToken(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String refreshToken
    ) {
        return validateToken(refreshToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretsConfig.getRefresh())));
    }

    private boolean validateToken(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String token,
            @NotNull(message = SECRET_KEY_NOT_NULL)
            SecretKey secret
    ) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);

            Claims claims = getClaims(token, secret);
            Date now = new Date();
            return !claims.getExpiration().before(now) && !claims.getIssuedAt().after(now) &&
                    !claims.getNotBefore().after(now);
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAccessClaims(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String token
    ) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretsConfig.getAccess())));
    }

    public Claims getRefreshClaims(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String token
    ) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretsConfig.getRefresh())));
    }

    private Claims getClaims(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String token,
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            SecretKey secret
    ) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
