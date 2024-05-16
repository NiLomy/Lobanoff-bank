package ru.kpfu.itis.lobanov.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountTypeDto {
    private String id;
    private String name;
}
