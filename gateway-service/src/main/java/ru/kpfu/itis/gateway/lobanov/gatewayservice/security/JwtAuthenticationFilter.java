package ru.kpfu.itis.gateway.lobanov.gatewayservice.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.utils.JwtProvider;

import java.io.IOException;

import static ru.kpfu.itis.gateway.lobanov.gatewayservice.utils.NamingConstants.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token == null || !token.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(BEARER_PREFIX.length());

        JwtAuthentication authentication = new JwtAuthentication(token);

        Claims claims;
        try {
            claims = jwtProvider.getAccessClaims(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get(EMAIL_KEY, String.class));

        authentication.setUserDetails(userDetails);

        authentication.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
