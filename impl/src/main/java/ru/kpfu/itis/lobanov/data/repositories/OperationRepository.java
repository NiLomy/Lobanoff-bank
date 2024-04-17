package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Operation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select o from Operation o where o.from = (select b from BankAccount b where b.owner = :userId)")
    List<Operation> findAllByUser(Long userId);

    @Query("select o from Operation o where o.from = (select b from BankAccount b where b.id = :bankAccountId) order by o.date desc limit 10")
    List<Operation> findAllByUserLimitRecent(Long bankAccountId);

    @Query("select o from Operation o where o.from = :from and o.date > :date")
    List<Operation> findAllByFromAndDateAfter(BankAccount from, LocalDate date);

    @Query("select o from Operation o where o.to = :to and o.date > :date")
    List<Operation> findAllByToAndDateAfter(BankAccount to, LocalDate date);
}
