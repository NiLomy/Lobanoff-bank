package ru.kpfu.itis.lobanov.finalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.finalservice.entities.Transaction;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select avg(t.initAmount) from Transaction t")
    BigDecimal calcAverageAmount();
}
