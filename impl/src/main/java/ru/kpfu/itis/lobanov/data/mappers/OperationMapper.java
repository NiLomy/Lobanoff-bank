package ru.kpfu.itis.lobanov.data.mappers;

import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.dtos.OperationDto;

import java.util.List;

public interface OperationMapper {
    OperationDto toResponse(Operation operation);

    List<OperationDto> toListResponse(List<Operation> set);
}
