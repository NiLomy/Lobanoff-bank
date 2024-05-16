package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.CurrencyApi;
import ru.kpfu.itis.lobanov.data.services.CurrencyService;
import ru.kpfu.itis.lobanov.dtos.CurrencyDto;
import ru.kpfu.itis.lobanov.dtos.requests.ConvertCurrenciesRequest;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController implements CurrencyApi {
    private final CurrencyService currencyService;

    @Override
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        return new ResponseEntity<>(currencyService.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> convertCurrencies(ConvertCurrenciesRequest request) {
        BigDecimal value = currencyService.convert(
                request.getCurrencyFrom(), request.getCurrencyTo(), request.getAmount()
        );

        return new ResponseEntity<>(value.toPlainString(), HttpStatus.OK);
    }
}
