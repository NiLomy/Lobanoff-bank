package ru.kpfu.itis.lobanov;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.data.entities.Currency;
import ru.kpfu.itis.lobanov.data.repositories.CurrencyRepository;
import ru.kpfu.itis.lobanov.data.services.CurrencyApiService;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {
    private final CurrencyApiService currencyApiService;
    private final CurrencyRepository currencyRepository;
    @GetMapping
    public String get() {
        return "AAAAAAAA";
    }

    @GetMapping("/{id}")
    public String getB(@PathVariable int id) {
        return "" + id;
    }

    @GetMapping("/a")
    public String aaa() throws IOException {
        String json = currencyApiService.getAllCurrencies();
        Set<Map.Entry<String, JsonElement>> map = JsonParser.parseString(json).getAsJsonObject().get("data").getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : map) {
            if (entry.getKey().length() >= 3){
                Currency currency = Currency.builder()
                        .name(entry.getKey())
                        .isoCode2(entry.getKey().substring(0, 2))
                        .isoCode3(entry.getKey().substring(0, 3))
                        .icon("")
                        .build();
                currencyRepository.save(currency);
            }
        }
        return "success";
    }
}
