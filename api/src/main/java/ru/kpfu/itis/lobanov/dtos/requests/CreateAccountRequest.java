package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    private String name;
    private String currencyId;
    private String typeId;
    private String ownerId;
}
