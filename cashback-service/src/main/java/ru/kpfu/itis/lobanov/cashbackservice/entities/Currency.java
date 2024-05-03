package ru.kpfu.itis.lobanov.cashbackservice.entities;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String isoCode;

    private String icon;

    @NonNull
    private Timestamp rowChangeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (!Objects.equals(id, currency.id)) return false;
        if (!Objects.equals(name, currency.name)) return false;
        if (!Objects.equals(isoCode, currency.isoCode)) return false;
        if (!Objects.equals(icon, currency.icon)) return false;
        return rowChangeTime.equals(currency.rowChangeTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isoCode != null ? isoCode.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + rowChangeTime.hashCode();
        return result;
    }
}
