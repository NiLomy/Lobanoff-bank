package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.CardInfoApi;
import ru.kpfu.itis.lobanov.data.services.CardInfoService;
import ru.kpfu.itis.lobanov.dtos.CardInfoDto;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CardInfoController implements CardInfoApi {
    private final CardInfoService cardInfoService;

    @Override
    public ResponseEntity<Map<String, CardInfoDto>> getAllCardInfo() {
        return new ResponseEntity<>(cardInfoService.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> uploadInfo() {
        cardInfoService.uploadInfo();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
