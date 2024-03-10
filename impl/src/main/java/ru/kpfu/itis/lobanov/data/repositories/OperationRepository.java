package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.lobanov.data.entities.Operation;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select o from Operation o where o.from = (select b from BankAccount b where b.owner = :userId)")
    List<Operation> findAllByUser(Long userId);

    @Query("select o from Operation o where o.from = (select b from BankAccount b where b.id = :bankAccountId) order by o.date desc limit 10")
    List<Operation> findAllByUserLimitRecent(Long bankAccountId);
}
