package ru.kpfu.itis.lobanov.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class BankInfoDto {
    @NotNull
    private String bin;

    @NotNull
    private String brand;

    @NotNull
    private String type;

    @NotNull
    private String category;

    @NotNull
    private String issuer;

    @NotNull
    private String alpha2;

    @NotNull
    private String alpha3;

    @NotNull
    private String country;
}
