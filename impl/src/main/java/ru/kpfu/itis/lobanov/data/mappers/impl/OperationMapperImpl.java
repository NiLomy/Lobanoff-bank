package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.mappers.OperationMapper;
import ru.kpfu.itis.lobanov.dtos.OperationDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationMapperImpl implements OperationMapper {
    @Override
    public OperationDto toResponse(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .amount(operation.getAmount())
                .date(operation.getDate())
                .build();
    }

    @Override
    public List<OperationDto> toListResponse(List<Operation> set) {
        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
