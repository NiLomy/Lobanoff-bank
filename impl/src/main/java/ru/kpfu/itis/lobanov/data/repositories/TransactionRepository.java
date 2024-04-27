package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Operation;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Operation, Long> {
    @Query("select o from Operation o where o.from = (select b from BankAccount b where b.owner = :userId)")
    List<Operation> findAllByUser(Long userId);

    @Query("select o from Operation o where o.from = (select b from BankAccount b where b.id = :bankAccountId) order by o.date desc limit 10")
    List<Operation> findAllByUserLimitRecent(Long bankAccountId);

    @Query("select o from Operation o where o.from = :from and o.date between :dateFrom and :dateTo")
    List<Operation> findAllByAccountFromAndBetweenDates(BankAccount from, LocalDate dateFrom, LocalDate dateTo);

    @Query("select o from Operation o where o.to = :to and o.date between :dateFrom and :dateTo")
    List<Operation> findAllByAccountToAndBetweenDates(BankAccount to, LocalDate dateFrom, LocalDate dateTo);

    @Query("select s1.amount - s2.amount from (select sum(o.amount) as amount from Operation o where o.date < :date and o.to = :account) as s1, (select sum(o.amount) as amount from Operation o where o.date < :date and o.from = :account) as s2")
    Long getBeginningMonthDeposit(BankAccount account, LocalDate date);
}
