package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CurrencyDto;
import ru.kpfu.itis.lobanov.dtos.PassportDto;
import ru.kpfu.itis.lobanov.dtos.requests.ConvertCurrenciesRequest;
import ru.kpfu.itis.lobanov.dtos.requests.SavePassportRequest;
import ru.kpfu.itis.lobanov.dtos.requests.UpdatePassportRequest;

@Tag(name = "Currency Api", description = "Provides methods for currencies")
@RequestMapping(path = "/api/v1/currencies", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface CurrencyApi {
    @PostMapping("/convert")
    ResponseEntity<String> convertCurrencies(@RequestBody ConvertCurrenciesRequest request);
}
