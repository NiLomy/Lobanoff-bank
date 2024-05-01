package ru.kpfu.itis.lobanov.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.lobanov.data.mappers.*;
import ru.kpfu.itis.lobanov.data.mappers.impl.*;

import java.util.Random;

@Configuration
public class MapperConfig {
    @Bean
    public Random random() {
        return new Random(System.currentTimeMillis());
    }
}
