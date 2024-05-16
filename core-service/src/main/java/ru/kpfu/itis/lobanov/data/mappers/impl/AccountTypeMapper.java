package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.AccountType;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.AccountTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountTypeMapper implements Mapper<AccountType, AccountTypeDto> {
    @Override
    public AccountTypeDto toResponse(AccountType type) {
        if (type == null) return null;

        return AccountTypeDto.builder()
                .id(String.valueOf(type.getId()))
                .name(type.getName())
                .build();
    }

    @Override
    public List<AccountTypeDto> toListResponse(List<AccountType> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
