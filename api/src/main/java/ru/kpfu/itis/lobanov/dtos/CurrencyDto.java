package ru.kpfu.itis.lobanov.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private Long id;
    private String name;
    private String icon;
}
