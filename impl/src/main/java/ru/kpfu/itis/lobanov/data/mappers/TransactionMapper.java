package ru.kpfu.itis.lobanov.data.mappers;

import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;

import java.util.List;

public interface TransactionMapper {
    TransactionDto toResponse(Operation operation);

    List<TransactionDto> toListResponse(List<Operation> set);
}
