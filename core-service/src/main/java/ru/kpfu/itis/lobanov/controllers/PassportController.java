package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.PassportApi;
import ru.kpfu.itis.lobanov.data.services.PassportService;
import ru.kpfu.itis.lobanov.dtos.PassportDto;
import ru.kpfu.itis.lobanov.dtos.requests.SavePassportRequest;
import ru.kpfu.itis.lobanov.dtos.requests.UpdatePassportRequest;

@RestController
@RequiredArgsConstructor
public class PassportController implements PassportApi {
    private final PassportService passportService;

    @Override
    public ResponseEntity<PassportDto> getPassportById(String passportId) {
        try {
            PassportDto passport = passportService.getById(Long.parseLong(passportId));

            if (passport == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(passport, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<PassportDto> savePassport(SavePassportRequest request) {
        try {
            PassportDto passport = passportService.save(request);

            if (passport == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(passport, HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<PassportDto> updatePassport(UpdatePassportRequest request) {
        try {
            PassportDto passport = passportService.update(request);

            if (passport == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(passport, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
