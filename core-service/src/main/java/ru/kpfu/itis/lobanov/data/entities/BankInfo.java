package ru.kpfu.itis.lobanov.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card_infos")
public class BankInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
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
    @Column(name = "alpha_2", nullable = false)
    private String alpha2;

    @NotNull
    @Column(name = "alpha_3", nullable = false)
    private String alpha3;

    @NotNull
    private String country;
}
