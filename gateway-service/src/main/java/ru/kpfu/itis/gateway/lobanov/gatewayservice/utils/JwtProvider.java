package ru.kpfu.itis.gateway.lobanov.gatewayservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.entities.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret.access}")
    private String accessSecret;
    @Value("${jwt.secret.refresh}")
    private String refreshSecret;

    public String generateAccessToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("authority", user.getRole().name())
                .claim("enabled", user.isEnabled())
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret)))
                .compact();
    }

    public String generateRefreshToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret)))
                .compact();
    }

    public boolean validateAccessToken(String accessToken, String email) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
        return validateToken(accessToken, secret) && getClaims(accessToken, secret).get("email", String.class).equals(email);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret)));
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
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret)));
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret)));
    }

    private Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
