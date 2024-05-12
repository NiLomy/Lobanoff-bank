package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.CurrencyApi;
import ru.kpfu.itis.lobanov.data.services.CurrencyConverterService;
import ru.kpfu.itis.lobanov.dtos.requests.ConvertCurrenciesRequest;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyController implements CurrencyApi {
    private final CurrencyConverterService currencyConverterService;

    @Override
    public ResponseEntity<String> convertCurrencies(ConvertCurrenciesRequest request) {
        BigDecimal value = currencyConverterService.convert(
                request.getCurrencyFrom(), request.getCurrencyTo(), request.getAmount()
        );

        return new ResponseEntity<>(value.toPlainString(), HttpStatus.OK);
    }
}
