package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper implements Mapper<Account, BankAccountDto> {
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<Card, CardDto> cardMapper;
    private final Mapper<Transaction, TransactionDto> transactionMapper;
    private final Mapper<Currency, CurrencyDto> currencyMapper;

    @Override
    public BankAccountDto toResponse(Account account) {
        return BankAccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .deposit(account.getDeposit())
                .currency(currencyMapper.toResponse(account.getCurrency()))
                .type(account.getType().getName())
                .owner(userMapper.toResponse(account.getOwner()))
                .cards(cardMapper.toListResponse(account.getCards()))
                .operations(transactionMapper.toListResponse(account.getTransactions()))
                .build();
    }

    @Override
    public List<BankAccountDto> toListResponse(List<Account> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
