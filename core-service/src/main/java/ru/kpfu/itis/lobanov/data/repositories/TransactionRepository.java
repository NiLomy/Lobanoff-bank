package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.from = (select b.id from BankAccount b where b.owner.id = :userId)")
    List<Transaction> findAllByUser(Long userId);

    @Query("select t from Transaction t where t.from = (select b.id from BankAccount b where b.id = :bankAccountId) order by t.date desc limit 10")
    List<Transaction> findAllByUserLimitRecent(Long bankAccountId);

    @Query("select t from Transaction t where t.from = :from and t.date between :dateFrom and :dateTo")
    List<Transaction> findAllByAccountFromAndBetweenDates(BankAccount from, LocalDate dateFrom, LocalDate dateTo);

    @Query("select t from Transaction t where t.to = :to and t.date between :dateFrom and :dateTo")
    List<Transaction> findAllByAccountToAndBetweenDates(BankAccount to, LocalDate dateFrom, LocalDate dateTo);

    @Query("select s1.amount - s2.amount from (select sum(t.totalAmount) as amount from Transaction t where t.date < :date and t.to = :account) as s1, (select sum(t.totalAmount) as amount from Transaction t where t.date < :date and t.from = :account) as s2")
    BigDecimal getBeginningMonthDeposit(BankAccount account, LocalDate date);

    @Query("select sum(t.cashback) from Transaction t inner join fetch BankAccount b on t.from = b.id where t.from = :accountId and t.date between :dateFrom and :dateTo")
    BigDecimal getCashbackBetweenDates(Long accountId, LocalDate dateFrom, LocalDate dateTo);
}
