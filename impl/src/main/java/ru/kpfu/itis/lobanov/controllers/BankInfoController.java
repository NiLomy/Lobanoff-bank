package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.BankInfoApi;
import ru.kpfu.itis.lobanov.data.services.BankInfoService;
import ru.kpfu.itis.lobanov.dtos.BankInfoDto;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BankInfoController implements BankInfoApi {
    private final BankInfoService bankInfoService;

    @Override
    public ResponseEntity<Map<String, BankInfoDto>> getAllBankInfo() {
        return new ResponseEntity<>(bankInfoService.getAll(), HttpStatus.OK);
    }

    @Override
    public String uploadInfo() {
        bankInfoService.uploadInfo();
        return "success";
    }
}
