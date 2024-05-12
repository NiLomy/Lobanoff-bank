package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.configs.CurrencyApiConfig;
import ru.kpfu.itis.lobanov.data.services.CurrencyApiService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CurrencyApiServiceImpl implements CurrencyApiService {
    private final OkHttpClient okHttpClient;
    private final CurrencyApiConfig currencyApiConfig;

    @Override
    public String getAllCurrencies() throws IOException {
        String url = currencyApiConfig.getUrl() + currencyApiConfig.getKey();
        Request request = new Request.Builder().url(url).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected response: " + response.code());
            }
        }
    }
}
