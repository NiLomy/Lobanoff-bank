package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.BankInfo;
import ru.kpfu.itis.lobanov.data.entities.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
