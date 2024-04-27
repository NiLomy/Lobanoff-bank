package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

@Tag(name = "Bank Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/requisites", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface RequisitesApi {
    @GetMapping("/{id}")
    ResponseEntity<RequisitesDto> getRequisitesById(@PathVariable("id") String requisitesId);

    @GetMapping("/account/{id}")
    ResponseEntity<RequisitesDto> getRequisitesByAccountId(@PathVariable("id") String accountId);
}
