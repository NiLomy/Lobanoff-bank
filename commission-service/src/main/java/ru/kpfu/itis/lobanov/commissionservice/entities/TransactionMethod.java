package ru.kpfu.itis.lobanov.commissionservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

import static ru.kpfu.itis.lobanov.commissionservice.utils.ValidationMessages.NAME_NOT_BLANK;
import static ru.kpfu.itis.lobanov.commissionservice.utils.ValidationMessages.NAME_NOT_NULL;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMethod {
    private Long id;

    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    private String description;

    private Timestamp rowChangeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionMethod that = (TransactionMethod) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(description, that.description)) return false;
        return rowChangeTime.equals(that.rowChangeTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + rowChangeTime.hashCode();
        return result;
    }
}
