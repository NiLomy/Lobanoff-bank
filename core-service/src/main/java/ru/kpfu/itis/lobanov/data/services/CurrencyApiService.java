package ru.kpfu.itis.lobanov.data.services;

import java.io.IOException;

public interface CurrencyApiService {
    String getAllCurrencies() throws IOException;
}
