package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.services.CurrencyService;
import ru.kpfu.itis.lobanov.dtos.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.CURRENCY_RUBLES_ISO_CODE_3;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountMapper implements Mapper<Account, AccountDto> {
    private final CurrencyService currencyService;
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<Card, CardDto> cardMapper;
    private final Mapper<Currency, CurrencyDto> currencyMapper;

    @Override
    public AccountDto toResponse(Account account) {
        if (account == null) return null;
        log.info("started mapping...");

        AccountDto.AccountDtoBuilder accountDtoBuilder = AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .currency(currencyMapper.toResponse(account.getCurrency()))
                .type(account.getType().getName())
                .owner(userMapper.toResponse(account.getOwner()))
                .cards(cardMapper.toListResponse(account.getCards()))
                .main(account.getMain());

        List<Transaction> transactions = account.getTransactions();
        if (transactions != null && !transactions.isEmpty()) {
            BigDecimal transfer = BigDecimal.ZERO;
            BigDecimal replenishment = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                if (!transaction.getCurrency().equals(account.getCurrency())) {
                    if (transaction.getTotalAmount() != null) {
                        transaction.setTotalAmount(currencyService.convert(
                                transaction.getCurrency().getIsoCode3(),
                                account.getCurrency().getIsoCode3(),
                                transaction.getTotalAmount()
                        ));
                    } else {
                        transaction.setInitAmount(currencyService.convert(
                                transaction.getCurrency().getIsoCode3(),
                                account.getCurrency().getIsoCode3(),
                                transaction.getInitAmount()
                        ));
                    }
                }
                if (Objects.equals(transaction.getFrom(), account.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        transfer = transfer.add(transaction.getInitAmount());
                    } else {
                        transfer = transfer.add(transaction.getTotalAmount());
                    }
                } else if (Objects.equals(transaction.getTo(), account.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        replenishment = replenishment.add(transaction.getInitAmount());
                    } else {
                        replenishment = replenishment.add(transaction.getTotalAmount());
                    }
                }
            }
            BigDecimal deposit = replenishment.subtract(transfer);
            accountDtoBuilder
                    .deposit(account.getDeposit().add(deposit));
        } else {
            accountDtoBuilder.deposit(account.getDeposit());
        }

        log.info("mapping end.");
        return accountDtoBuilder.build();
    }

    @Override
    public List<AccountDto> toListResponse(List<Account> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
