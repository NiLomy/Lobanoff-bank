package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.data.entities.Currency;
import ru.kpfu.itis.lobanov.data.entities.Transaction;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.AccountRepository;
import ru.kpfu.itis.lobanov.dtos.AccountDto;
import ru.kpfu.itis.lobanov.dtos.CurrencyDto;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionMapper implements Mapper<Transaction, TransactionDto> {
    private final Mapper<Currency, CurrencyDto> currencyMapper;
    private final Mapper<Account, AccountDto> accountMapper;
    private final AccountRepository accountRepository;

    @Override
    public TransactionDto toResponse(Transaction transaction) {
        if (transaction == null) return null;

        TransactionDto.TransactionDtoBuilder transactionBuilder = TransactionDto.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .currency(currencyMapper.toResponse(transaction.getCurrency()))
                .type(transaction.getType().getName())
                .from(accountMapper.toResponse(accountRepository.findById(transaction.getFrom()).orElse(null)))
                .to(accountMapper.toResponse(accountRepository.findById(transaction.getTo()).orElse(null)))
                .message(transaction.getMessage())
                .cashback(transaction.getCashback())
                .amount(transaction.getTotalAmount());

        if (transaction.getTotalAmount() == null) {
            transactionBuilder.amount(transaction.getInitAmount());
        }

        if (transaction.getCategory() != null) {
            transactionBuilder.category(transaction.getCategory().getTitle());
        }

        return transactionBuilder.build();
    }

    @Override
    public List<TransactionDto> toListResponse(List<Transaction> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
