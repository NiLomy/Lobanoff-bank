package ru.kpfu.itis.lobanov.filters;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.utils.CurrentUserContext;
import ru.kpfu.itis.lobanov.utils.JwtProvider;

import java.io.IOException;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.EMAIL_KEY;

@Component
@RequiredArgsConstructor
public class UserIdentifierFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token == null || !token.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(BEARER.length());

        Claims claims;
        try {
            claims = jwtProvider.getAccessClaims(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        User currentUser = userRepository.findByEmail(claims.get(EMAIL_KEY, String.class)).orElse(null);

        CurrentUserContext.setCurrentUser(currentUser);

        filterChain.doFilter(request, response);
    }
}
