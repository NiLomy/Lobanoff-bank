package ru.kpfu.itis.chat.lobanov.chatservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ChatServiceConstants.*;

@Configuration
public class SecurityConfig {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern(ORIGIN_PATTERN);
        config.addAllowedHeader(ALLOWED_HEADERS_PATTERN);
        config.addAllowedMethod(OPTIONS_METHOD_NAME);
        config.addAllowedMethod(HEAD_METHOD_NAME);
        config.addAllowedMethod(GET_METHOD_NAME);
        config.addAllowedMethod(PUT_METHOD_NAME);
        config.addAllowedMethod(POST_METHOD_NAME);
        config.addAllowedMethod(DELETE_METHOD_NAME);
        config.addAllowedMethod(PATCH_METHOD_NAME);
        source.registerCorsConfiguration(CORS_CONFIGURATION_PATTERN, config);
        return new CorsFilter(source);
    }
}
