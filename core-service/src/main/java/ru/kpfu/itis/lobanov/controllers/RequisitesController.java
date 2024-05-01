package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.RequisitesApi;
import ru.kpfu.itis.lobanov.data.services.RequisitesService;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

@RestController
@RequiredArgsConstructor
public class RequisitesController implements RequisitesApi {
    private final RequisitesService requisitesService;

    @Override
    public ResponseEntity<RequisitesDto> getRequisitesById(String requisitesId) {
        RequisitesDto requisites = requisitesService.getById(Long.parseLong(requisitesId));

        if (requisites == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(requisites, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RequisitesDto> getRequisitesByAccountId(String accountId) {
        RequisitesDto requisites = requisitesService.getRequisites(Long.parseLong(accountId));

        if (requisites == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(requisites, HttpStatus.OK);
    }
}
