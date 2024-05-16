package ru.kpfu.itis.lobanov.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PassportDto {
    private String id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String lastname;
    private String patronymic;
    @NotNull
    @PositiveOrZero
    private Short series;
    @NotNull
    @PositiveOrZero
    private Integer number;
    @NotNull
    private Date birthday;
    @NotNull
    private Character gender;
    @NotNull
    @NotBlank
    private String departmentCode;
    @NotNull
    @NotBlank
    private String issuedBy;
    @NotNull
    private Date issuedDate;
    @NotNull
    @NotBlank
    private String address;
}
