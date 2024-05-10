package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.BankInfoDto;

import java.util.List;
import java.util.Map;

@Tag(name = "Bank Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/card-info", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface BankInfoApi {

    @GetMapping("/all")
    ResponseEntity<Map<String, BankInfoDto>> getAllBankInfo();

    // TODO use webflux here
    @GetMapping("/upload")
    String uploadInfo();
}
