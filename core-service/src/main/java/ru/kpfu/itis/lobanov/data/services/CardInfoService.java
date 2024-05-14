package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.CardInfoDto;

import java.util.Map;

public interface CardInfoService {
    Map<String, CardInfoDto> getAll();

    void uploadInfo();
}
