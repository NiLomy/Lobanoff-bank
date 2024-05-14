package ru.kpfu.itis.lobanov.cashbackservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

import static ru.kpfu.itis.lobanov.cashbackservice.utils.ValidationMessages.TITLE_NOT_BLANK;
import static ru.kpfu.itis.lobanov.cashbackservice.utils.ValidationMessages.TITLE_NOT_NULL;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;

    @NotNull(message = TITLE_NOT_NULL)
    @NotBlank(message = TITLE_NOT_BLANK)
    private String title;

    private String icon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!Objects.equals(id, category.id)) return false;
        if (!Objects.equals(title, category.title)) return false;
        return Objects.equals(icon, category.icon);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }
}
