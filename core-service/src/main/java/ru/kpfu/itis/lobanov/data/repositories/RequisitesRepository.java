package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Requisites;

public interface RequisitesRepository extends JpaRepository<Requisites, Long> {
    Requisites findByPayeeAccount(BankAccount payeeAccount);
}
