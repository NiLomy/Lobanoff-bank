package ru.kpfu.itis.lobanov.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Date;

@Data
public final class SavePassportRequest {
    @NotNull(message = "User name should not be null.")
    @NotBlank(message = "User name should not be empty.")
    @Schema(description = "Name of the user.", example = "Ivan")
    private String name;
    @NotNull(message = "User lastname should not be null.")
    @NotBlank(message = "User lastname should not be empty.")
    @Schema(description = "Lastname of the user.", example = "Ivanov")
    private String lastname;
    @Schema(description = "Patronymic of the user.", example = "Jovanovich")
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
