package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseAccountRequest {
    private String accountId;
}
