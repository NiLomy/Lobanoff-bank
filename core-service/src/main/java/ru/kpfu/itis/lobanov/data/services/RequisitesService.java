package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

public interface RequisitesService {
    RequisitesDto getById(Long requisitesId);

    RequisitesDto getRequisites(Long accountId);
}
