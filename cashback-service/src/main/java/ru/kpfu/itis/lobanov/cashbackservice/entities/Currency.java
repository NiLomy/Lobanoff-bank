package ru.kpfu.itis.lobanov.cashbackservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

import static ru.kpfu.itis.lobanov.cashbackservice.utils.ValidationMessages.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private Long id;

    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotNull(message = ISO_CODE_NOT_NULL)
    @NotBlank(message = ISO_CODE_NOT_BLANK)
    private String isoCode2;

    @NotNull(message = ISO_CODE_NOT_NULL)
    @NotBlank(message = ISO_CODE_NOT_BLANK)
    private String isoCode3;

    private String icon;

    private Timestamp rowChangeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (!Objects.equals(id, currency.id)) return false;
        if (!Objects.equals(name, currency.name)) return false;
        if (!Objects.equals(isoCode2, currency.isoCode2)) return false;
        if (!Objects.equals(isoCode3, currency.isoCode3)) return false;
        if (!Objects.equals(icon, currency.icon)) return false;
        return Objects.equals(rowChangeTime, currency.rowChangeTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isoCode2 != null ? isoCode2.hashCode() : 0);
        result = 31 * result + (isoCode3 != null ? isoCode3.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (rowChangeTime != null ? rowChangeTime.hashCode() : 0);
        return result;
    }
}
