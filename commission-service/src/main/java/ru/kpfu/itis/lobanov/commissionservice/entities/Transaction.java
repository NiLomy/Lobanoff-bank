package ru.kpfu.itis.lobanov.commissionservice.entities;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Transaction.class)
public class Transaction implements Serializable {
    private Long id;

    @NotNull
    private Timestamp date;

    @NotNull
    private BigDecimal initAmount;

    private Currency currency;

    private TransactionType type;

    private TransactionMethod method;

    private Long from;

    private Long to;

    @NotNull
    private String bankNameFrom;

    @NotNull
    private String bankNameTo;

    private String terminalIp;

    private String serviceCompany;

    private String message;

    private BigDecimal commission;

    private BigDecimal cashback;

    private Integer riskIndicator;

    private BigDecimal totalAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(initAmount, that.initAmount)) return false;
        if (!Objects.equals(currency, that.currency)) return false;
        if (!Objects.equals(type, that.type)) return false;
        if (!Objects.equals(method, that.method)) return false;
        if (!Objects.equals(from, that.from)) return false;
        if (!Objects.equals(to, that.to)) return false;
        if (!Objects.equals(bankNameFrom, that.bankNameFrom)) return false;
        if (!Objects.equals(bankNameTo, that.bankNameTo)) return false;
        if (!Objects.equals(terminalIp, that.terminalIp)) return false;
        if (!Objects.equals(serviceCompany, that.serviceCompany))
            return false;
        if (!Objects.equals(message, that.message)) return false;
        if (!Objects.equals(commission, that.commission)) return false;
        if (!Objects.equals(cashback, that.cashback)) return false;
        if (!Objects.equals(riskIndicator, that.riskIndicator))
            return false;
        return Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (initAmount != null ? initAmount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (bankNameFrom != null ? bankNameFrom.hashCode() : 0);
        result = 31 * result + (bankNameTo != null ? bankNameTo.hashCode() : 0);
        result = 31 * result + (terminalIp != null ? terminalIp.hashCode() : 0);
        result = 31 * result + (serviceCompany != null ? serviceCompany.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (cashback != null ? cashback.hashCode() : 0);
        result = 31 * result + (riskIndicator != null ? riskIndicator.hashCode() : 0);
        result = 31 * result + (totalAmount != null ? totalAmount.hashCode() : 0);
        return result;
    }
}
