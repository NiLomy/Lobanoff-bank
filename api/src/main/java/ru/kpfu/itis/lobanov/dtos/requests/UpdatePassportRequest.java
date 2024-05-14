package ru.kpfu.itis.lobanov.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Date;

@Data
public final class UpdatePassportRequest {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String lastname;
    @NotNull
    @NotBlank
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
