package ru.kpfu.itis.lobanov.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CardInfoDto {
    @NotNull
    @NotBlank
    private String bin;
    @NotNull
    @NotBlank
    private String brand;
    @NotNull
    @NotBlank
    private String type;
    @NotNull
    @NotBlank
    private String category;
    @NotNull
    @NotBlank
    private String issuer;
    @NotNull
    @NotBlank
    private String alpha2;
    @NotNull
    @NotBlank
    private String alpha3;
    @NotNull
    @NotBlank
    private String country;
}
