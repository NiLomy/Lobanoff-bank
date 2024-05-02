package ru.kpfu.itis.lobanov.data.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Transaction.class)
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreationTimestamp(source = SourceType.DB)
    private Timestamp date;

    @NotNull
    @Column(name = "init_amount")
    private BigDecimal initAmount;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private TransactionType type;

    @ManyToOne
    private TransactionMethod method;

    private Long from;

    private Long to;

    @NotNull
    @Column(name = "bank_name_from")
    private String bankNameFrom;

    @NotNull
    @Column(name = "bank_name_to")
    private String bankNameTo;

    @Column(name = "terminal_ip")
    private String terminalIp;

    @Column(name = "service_company")
    private String serviceCompany;

    @Column(length = 150)
    private String message;

    private BigDecimal commission;

    private BigDecimal cashback;

    @Column(name = "risk_indicator")
    private Integer riskIndicator;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transaction transaction = (Transaction) o;
        return getId() != null && Objects.equals(getId(), transaction.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
