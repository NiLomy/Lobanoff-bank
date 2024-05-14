package ru.kpfu.itis.gateway.lobanov.gatewayservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.configs.JwtConfig;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.entities.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static ru.kpfu.itis.gateway.lobanov.gatewayservice.utils.NamingConstants.*;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    public static final int ACCESS_TOKEN_LIVE_MINUTES = 5;
    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(ACCESS_TOKEN_LIVE_MINUTES).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim(EMAIL_KEY, user.getEmail())
                .claim(AUTHORITY_KEY, user.getRole().name())
                .claim(ENABLED_KEY, user.isEnabled())
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getAccess())))
                .compact();
    }

    public String generateRefreshToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMonths(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getRefresh())))
                .compact();
    }

    public boolean validateAccessToken(String accessToken, String email) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getAccess()));
        return validateToken(accessToken, secret) && getClaims(accessToken, secret).get(EMAIL_KEY, String.class).equals(email);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getRefresh())));
    }

    private boolean validateToken(String token, SecretKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);

            Claims claims = getClaims(token, secret);
            Date now = new Date();
            return !claims.getExpiration().before(now) && !claims.getIssuedAt().after(now) && !claims.getNotBefore().after(now);
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getAccess())));
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getRefresh())));
    }

    private Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
