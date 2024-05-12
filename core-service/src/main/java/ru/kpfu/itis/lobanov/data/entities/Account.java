package ru.kpfu.itis.lobanov.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotNull(message = NUMBER_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String number;

    @NotNull(message = AMOUNT_NOT_NULL)
    @PositiveOrZero(message = AMOUNT_POSITIVE_OR_ZERO)
    private BigDecimal deposit;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private AccountType type;

    @NotNull
    @ManyToOne
    private User owner;

    @OneToMany
    @ToString.Exclude
    private List<Transaction> transactions;

    @OneToMany
    @ToString.Exclude
    private List<Card> cards;

    @NotNull(message = MAIN_ACCOUNT_NOT_NULL)
    private Boolean main;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account that = (Account) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
