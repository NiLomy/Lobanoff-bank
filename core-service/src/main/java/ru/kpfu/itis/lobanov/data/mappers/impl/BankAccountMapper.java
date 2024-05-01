package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BankAccountMapper implements Mapper<BankAccount, BankAccountDto> {
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<Card, CardDto> cardMapper;
    private final Mapper<Operation, TransactionDto> transactionMapper;

    @Override
    public BankAccountDto toResponse(BankAccount bankAccount) {
        return BankAccountDto.builder()
                .id(bankAccount.getId())
                .name(bankAccount.getName())
                .deposit(bankAccount.getDeposit())
                .owner(userMapper.toResponse(bankAccount.getOwner()))
                .cards(cardMapper.toListResponse(bankAccount.getCards()))
                .operations(transactionMapper.toListResponse(bankAccount.getOperations()))
                .build();
    }

    @Override
    public List<BankAccountDto> toListResponse(List<BankAccount> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
