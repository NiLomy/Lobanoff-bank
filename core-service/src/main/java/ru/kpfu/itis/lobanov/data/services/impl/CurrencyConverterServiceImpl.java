package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.services.CurrencyApiService;
import ru.kpfu.itis.lobanov.data.services.CurrencyConverterService;
import ru.kpfu.itis.lobanov.exceptions.ApiException;
import ru.kpfu.itis.lobanov.utils.CurrencyJsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    public static final int AMOUNT_SCALE = 8;
    private final CurrencyApiService currencyApiService;
    private final CurrencyJsonParser parser;

    @Override
    public BigDecimal convert(String currencyFrom, String currencyTo, BigDecimal amount) {
        try {
            String json = currencyApiService.getAllCurrencies();
            BigDecimal valueFrom = parser.parseValue(json, currencyFrom);
            BigDecimal valueTo = parser.parseValue(json, currencyTo);
            return amount.multiply(valueTo).divide(valueFrom, AMOUNT_SCALE, RoundingMode.HALF_EVEN);
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
