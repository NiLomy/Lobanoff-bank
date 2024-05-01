//package ru.kpfu.itis.lobanov.filters;
//
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import ru.kpfu.itis.lobanov.security.JwtAuthentication;
//import ru.kpfu.itis.lobanov.utils.JwtProvider;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final UserDetailsService userDetailsService;
//    private final JwtProvider jwtProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (token == null || !token.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        token = token.substring(7);
//
//        JwtAuthentication authentication = new JwtAuthentication(token);
//
//        Claims claims;
//        try {
//            claims = jwtProvider.getAccessClaims(token);
//        } catch (Exception e) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("email", String.class));
//
//        authentication.setUserDetails(userDetails);
//
//        authentication.setAuthenticated(true);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        filterChain.doFilter(request, response);
//    }
//}
