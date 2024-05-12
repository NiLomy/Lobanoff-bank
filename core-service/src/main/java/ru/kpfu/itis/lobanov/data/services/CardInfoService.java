package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.BankInfoDto;

import java.util.Map;

public interface CardInfoService {
    Map<String, BankInfoDto> getAll();

    void uploadInfo();
}
