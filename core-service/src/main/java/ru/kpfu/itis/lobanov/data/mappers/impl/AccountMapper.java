package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper implements Mapper<Account, AccountDto> {
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<Card, CardDto> cardMapper;
    private final Mapper<Currency, CurrencyDto> currencyMapper;

    @Override
    public AccountDto toResponse(Account account) {
        if (account == null) return null;

        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .deposit(account.getTransactions().stream().map(Transaction::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add))
                .currency(currencyMapper.toResponse(account.getCurrency()))
                .type(account.getType().getName())
                .owner(userMapper.toResponse(account.getOwner()))
                .cards(cardMapper.toListResponse(account.getCards()))
                .main(account.getMain())
                .build();
    }

    @Override
    public List<AccountDto> toListResponse(List<Account> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
