package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BindCardRequest {
    private String accountId;
    private String cardId;
}
