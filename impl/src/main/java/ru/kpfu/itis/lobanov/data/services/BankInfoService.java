package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.BankInfoDto;

import java.util.List;
import java.util.Map;

public interface BankInfoService {
    Map<String, BankInfoDto> getAll();

    void uploadInfo();
}
