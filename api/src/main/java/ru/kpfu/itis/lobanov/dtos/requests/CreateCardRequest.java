package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardRequest {
    private String accountId;
}
