package ru.kpfu.itis.lobanov.data.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.validator.constraints.CreditCardNumber;
import ru.kpfu.itis.lobanov.utils.CardNumberMaskingSerializer;

import java.util.Objects;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.CVV_LENGTH;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreditCardNumber(message = CARD_NUMBER)
    @JsonSerialize(using = CardNumberMaskingSerializer.class)
    @NotNull(message = CARD_NUMBER_NOT_NULL)
    @NotBlank(message = CARD_NUMBER_NOT_BLANK)
    private String number;

    @NotNull(message = EXPIRATION_NOT_NULL)
    @NotBlank(message = EXPIRATION_NOT_BLANK)
    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([2-9][0-9])$",
            message = EXPIRATION_DATE_REGEX)
    private String expiration;

    @NotNull(message = CVV_NOT_NULL)
    @NotBlank(message = CVV_NOT_EMPTY)
    @Digits(integer = CVV_LENGTH, fraction = 0, message = CVV_SIZE)
    private String cvv;

    @ManyToOne
    private Account account;

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
