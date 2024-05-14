package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.requests.ConvertCurrenciesRequest;

@Tag(name = "Currency Api", description = "Provides methods for currencies")
@RequestMapping(path = "/api/v1/currencies", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface CurrencyApi {
    @Operation(summary = "Convert currencies.", description = "Returns amount in new currency.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Currency not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/convert")
    ResponseEntity<String> convertCurrencies(
            @Parameter(description = "Request for convert currencies.", required = true)
            @RequestBody
            ConvertCurrenciesRequest request
    );
}
