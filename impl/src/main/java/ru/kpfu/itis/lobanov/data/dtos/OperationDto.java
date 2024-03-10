package ru.kpfu.itis.lobanov.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.lobanov.data.entities.Operation;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private Long id;
    private Date date;
    private Long amount;

    public static OperationDto fromOperation(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .date(operation.getDate())
                .amount(operation.getAmount())
                .build();
    }
}
