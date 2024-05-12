package ru.kpfu.itis.lobanov.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Timestamp;
import java.util.Objects;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.NAME_NOT_BLANK;
import static ru.kpfu.itis.lobanov.utils.ValidationMessages.NAME_NOT_NULL;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    private String description;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "row_change_time")
    private Timestamp rowChangeTime;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AccountType that = (AccountType) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
