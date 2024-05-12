package ru.kpfu.itis.lobanov.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Component
@Validated
public class CurrencyJsonParser {
    public static final String CURRENCIES_DATA_KEY = "data";
    public static final String CURRENCY_VALUE_KEY = "value";

    public BigDecimal parseValue(
            @NotNull(message = JSON_NOT_NULL)
            @NotBlank(message = JSON_NOT_BLANK)
            String json,
            @NotNull(message = CURRENCY_CODE_NOT_NULL)
            @NotBlank(message = CURRENCY_CODE_NOT_BLANK)
            String code
    ) {
        JsonObject currJson = JsonParser.parseString(json).getAsJsonObject();
        JsonObject currencies = currJson.get(CURRENCIES_DATA_KEY).getAsJsonObject();
        return currencies.get(code).getAsJsonObject().get(CURRENCY_VALUE_KEY).getAsBigDecimal();
    }
}
