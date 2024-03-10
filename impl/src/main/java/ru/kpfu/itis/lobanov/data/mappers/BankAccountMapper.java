package ru.kpfu.itis.lobanov.data.mappers;

import org.springframework.data.domain.Page;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;

import java.util.List;

public interface BankAccountMapper {
    BankAccountDto toResponse(BankAccount bankAccount);
    List<BankAccountDto> toListResponse(List<BankAccount> set);
}
