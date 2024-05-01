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
public class BankAccountMapper implements Mapper<BankAccount, BankAccountDto> {
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<Card, CardDto> cardMapper;
    private final Mapper<Transaction, TransactionDto> transactionMapper;
    private final Mapper<Currency, CurrencyDto> currencyMapper;

    @Override
    public BankAccountDto toResponse(BankAccount bankAccount) {
        return BankAccountDto.builder()
                .id(bankAccount.getId())
                .name(bankAccount.getName())
                .deposit(bankAccount.getDeposit())
                .currency(currencyMapper.toResponse(bankAccount.getCurrency()))
                .type(bankAccount.getType().getName())
                .owner(userMapper.toResponse(bankAccount.getOwner()))
                .cards(cardMapper.toListResponse(bankAccount.getCards()))
                .operations(transactionMapper.toListResponse(bankAccount.getTransactions()))
                .build();
    }

    @Override
    public List<BankAccountDto> toListResponse(List<BankAccount> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
