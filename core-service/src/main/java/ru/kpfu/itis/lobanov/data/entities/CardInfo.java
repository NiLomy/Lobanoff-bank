package ru.kpfu.itis.lobanov.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card_infos")
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = BIN_NOT_NULL)
    @NotBlank(message = BIN_NOT_BLANK)
    private String bin;

    @NotNull(message = BRAND_NOT_NULL)
    @NotBlank(message = BRAND_NOT_BLANK)
    private String brand;

    @NotNull(message = CARD_CATEGORY_NOT_NULL)
    @NotBlank(message = CARD_TYPE_NOT_BLANK)
    private String type;

    @NotNull(message = CARD_CATEGORY_NOT_NULL)
    @NotBlank(message = CARD_CATEGORY_NOT_BLANK)
    private String category;

    @NotNull(message = ISSUER_NOT_NULL)
    @NotBlank(message = ISSUER_NOT_BLANK)
    private String issuer;

    @Column(name = "alpha_2", nullable = false)
    @NotNull(message = CARD_ALPHA_CODE_NOT_NULL)
    @NotBlank(message = CARD_ALPHA_CODE_NOT_BLANK)
    private String alpha2;

    @Column(name = "alpha_3", nullable = false)
    @NotNull(message = CARD_ALPHA_CODE_NOT_NULL)
    @NotBlank(message = CARD_ALPHA_CODE_NOT_BLANK)
    private String alpha3;

    @NotNull(message = COUNTRY_NOT_NULL)
    @NotBlank(message = COUNTRY_NOT_BLANK)
    private String country;
}
