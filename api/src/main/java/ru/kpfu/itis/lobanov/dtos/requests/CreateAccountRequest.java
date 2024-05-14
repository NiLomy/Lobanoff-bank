package ru.kpfu.itis.lobanov.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAccountRequest {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String currencyId;
    @NotNull
    @NotBlank
    private String typeId;
    @NotNull
    @NotBlank
    private String ownerId;
}
