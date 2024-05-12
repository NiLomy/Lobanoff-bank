package ru.kpfu.itis.lobanov.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.RANDOM_INSTANCE_WITH_BINDING;

@Configuration
public class RandomConfig {
    @Bean
    @Qualifier(RANDOM_INSTANCE_WITH_BINDING)
    public Random random() {
        return new Random(System.currentTimeMillis());
    }
}
