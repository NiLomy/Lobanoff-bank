package ru.kpfu.itis.gateway.lobanov.gatewayservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

import static ru.kpfu.itis.gateway.lobanov.gatewayservice.utils.ValidationMessages.*;

@Entity
@Table(name = "passports")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotNull(message = LASTNAME_NOT_NULL)
    @NotBlank(message = LASTNAME_NOT_BLANK)
    private String lastname;

    @NotNull(message = PATRONYMIC_NOT_NULL)
    @NotBlank(message = PATRONYMIC_NOT_BLANK)
    private String patronymic;

    @NotNull(message = SERIES_NOT_NULL)
    @PositiveOrZero(message = SERIES_POSITIVE_OR_ZERO)
    private Short series;

    @NotNull(message = NUMBER_NOT_NULL)
    @PositiveOrZero(message = NUMBER_POSITIVE_OR_ZERO)
    private Integer number;

    @NotNull(message = DATE_NOT_NULL)
    private Date birthday;

    @NotNull(message = GENDER_NOT_NULL)
    private Character gender;

    @NotNull(message = DEPARTMENT_CODE_NOT_NULL)
    @NotBlank(message = DEPARTMENT_CODE_NOT_BLANK)
    private String departmentCode;

    @NotNull(message = ISSUED_BY_NOT_NULL)
    @NotBlank(message = ISSUED_BY_NOT_BLANK)
    private String issuedBy;

    @NotNull(message = DATE_NOT_NULL)
    private Date issuedDate;

    @NotNull(message = ADDRESS_NOT_NULL)
    @NotBlank(message = ADDRESS_NOT_BLANK)
    private String address;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Passport passport = (Passport) o;
        return getId() != null && Objects.equals(getId(), passport.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
