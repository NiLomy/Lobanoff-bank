package ru.kpfu.itis.lobanov.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.lobanov.data.mappers.*;
import ru.kpfu.itis.lobanov.data.mappers.impl.*;

@Configuration
public class MapperConfig {
    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public OperationMapper operationMapper() {
        return new OperationMapperImpl();
    }

    @Bean
    public CardMapper cardMapper() {
        return new CardMapperImpl(userMapper());
    }

    @Bean
    public UserMessageMapper userMessageMapper() {
        return new UserMessageMapperImpl(userMapper());
    }

    @Bean
    public BankAccountMapper bankAccountMapper() {
        return new BankAccountMapperImpl(userMapper(), cardMapper(), operationMapper());
    }
}
