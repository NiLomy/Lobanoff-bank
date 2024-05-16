package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.AccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.CardRepository;
import ru.kpfu.itis.lobanov.data.services.AccountService;
import ru.kpfu.itis.lobanov.data.services.CardService;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateCardRequest;
import ru.kpfu.itis.lobanov.utils.CreditCardNumberGenerator;

import java.time.LocalDate;
import java.util.Random;

import static ru.kpfu.itis.lobanov.utils.BankingConstants.CREDIT_CARD_BIN;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CreditCardNumberGenerator creditCardNumberGenerator;
    private final Mapper<Card, CardDto> cardMapper;

    @Override
    public CardDto getById(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(IllegalArgumentException::new);
        return cardMapper.toResponse(card);
    }

    @Override
    public CardDto create(CreateCardRequest request) {
        Account account = accountRepository.findById(Long.parseLong(request.getAccountId())).orElseThrow(IllegalArgumentException::new);
        Random random = new Random();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear() % 100 + 4;
        int month = localDate.getMonthValue();
        int cvv = 100 + random.nextInt(900);
        Card card = cardRepository.save(
                Card.builder()
                        .number(creditCardNumberGenerator.generate(CREDIT_CARD_BIN, 16))
                        .expiration(String.format("%02d/%02d", month, year))
                        .cvv(String.valueOf(cvv))
                        .account(account)
                        .build()
        );

        accountService.bindCard(new BindCardRequest(String.valueOf(account.getId()), String.valueOf(card.getId())));
        return cardMapper.toResponse(card);
    }
}
