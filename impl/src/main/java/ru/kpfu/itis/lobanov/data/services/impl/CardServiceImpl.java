package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.mappers.CardMapper;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.CardRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.CardService;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.utils.CreditCardNumberGenerator;

import java.time.LocalDate;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    @Override
    public CardDto create(Long accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);
        Random random = new Random();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear() % 100 + 4;
        int month = localDate.getMonthValue();
        int cvv = 100 + random.nextInt(900);
        Card card = cardRepository.save(
                Card.builder()
                        .number(CreditCardNumberGenerator.generate("11", 16))
                        .expiration(String.format("%02d/%02d", month, year))
                        .cvv(String.valueOf(cvv))
                        .owner(bankAccount.getOwner())
                        .build()
        );

        return cardMapper.toResponse(card);
    }
}
