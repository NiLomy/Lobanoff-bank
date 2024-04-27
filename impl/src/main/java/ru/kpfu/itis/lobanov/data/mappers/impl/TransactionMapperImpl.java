package ru.kpfu.itis.lobanov.data.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.mappers.TransactionMapper;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public TransactionDto toResponse(Operation operation) {
        return TransactionDto.builder()
                .id(operation.getId())
                .amount(operation.getAmount())
                .date(operation.getDate())
                .build();
    }

    @Override
    public List<TransactionDto> toListResponse(List<Operation> set) {
        if (set == null) return null;
        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
