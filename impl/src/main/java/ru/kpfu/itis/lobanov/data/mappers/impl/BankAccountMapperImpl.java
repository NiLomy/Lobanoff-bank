package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.mappers.BankAccountMapper;
import ru.kpfu.itis.lobanov.data.mappers.CardMapper;
import ru.kpfu.itis.lobanov.data.mappers.OperationMapper;
import ru.kpfu.itis.lobanov.data.mappers.UserMapper;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BankAccountMapperImpl implements BankAccountMapper {
    private final UserMapper userMapper;
    private final CardMapper cardMapper;
    private final OperationMapper operationMapper;

    @Override
    public BankAccountDto toResponse(BankAccount bankAccount) {
        return BankAccountDto.builder()
                .id(bankAccount.getId())
                .name(bankAccount.getName())
                .deposit(bankAccount.getDeposit())
                .owner(userMapper.toResponse(bankAccount.getOwner()))
                .cards(cardMapper.toListResponse(bankAccount.getCards()))
                .operations(operationMapper.toListResponse(bankAccount.getOperations()))
                .beginningMonthDeposit(bankAccount.getBeginningMonthDeposit())
                .build();
    }

    @Override
    public List<BankAccountDto> toListResponse(List<BankAccount> set) {
        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
