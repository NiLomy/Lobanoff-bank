package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.CardInfoDto;

import java.util.Map;

@Tag(name = "Card Info Api", description = "Provides methods for card infos")
@RequestMapping(path = "/api/v1/card-info", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface CardInfoApi {
    @Operation(summary = "Get all card infos.", description = "Returns map of card bin and info.", responses = {
            @ApiResponse(responseCode = "200", description = "Operation was successful."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/all")
    ResponseEntity<Map<String, CardInfoDto>> getAllCardInfo();

    @Operation(summary = "Upload all card infos.", description = "Returns result message after infos uploading.", responses = {
            @ApiResponse(responseCode = "200", description = "Operation was successful."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/upload")
    ResponseEntity<String> uploadInfo();
}
