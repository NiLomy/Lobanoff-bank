package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.PassportDto;
import ru.kpfu.itis.lobanov.dtos.requests.SavePassportRequest;
import ru.kpfu.itis.lobanov.dtos.requests.UpdatePassportRequest;

@Tag(name = "Passport Api", description = "Provides methods for users passports")
@RequestMapping(path = "/api/v1/passports", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface PassportApi {
    @GetMapping("/{id}")
    ResponseEntity<PassportDto> getPassportById(@PathVariable("id") String passportId);

    @PostMapping("/save")
    ResponseEntity<PassportDto> savePassport(@RequestBody SavePassportRequest request);

    @PutMapping("/update")
    ResponseEntity<PassportDto> updatePassport(@RequestBody UpdatePassportRequest request);
}
