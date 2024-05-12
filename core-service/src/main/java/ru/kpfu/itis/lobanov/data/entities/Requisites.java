package ru.kpfu.itis.lobanov.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.CORRESPONDENT_ACCOUNT_LENGTH;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requisites")
public class Requisites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User payee;

    @OneToOne
    private Account payeeAccount;

    @Column(name = "corr_account", length = CORRESPONDENT_ACCOUNT_LENGTH)
    @NotNull(message = ACCOUNT_NOT_NULL)
    @NotBlank(message = ACCOUNT_NOT_BLANK)
    private String corrAccount;

    @NotNull(message = BANK_IDENTIFICATION_CODE_NOT_NULL)
    @NotBlank(message = BANK_IDENTIFICATION_CODE_NOT_BLANK)
    private String code;

    @NotNull(message = BANK_NAME_NOT_NULL)
    @NotBlank(message = BANK_NAME_NOT_BLANK)
    private String bankName;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Card card = (Card) o;
        return getId() != null && Objects.equals(getId(), card.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
