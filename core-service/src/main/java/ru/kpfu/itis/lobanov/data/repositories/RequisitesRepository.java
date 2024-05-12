package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.data.entities.Requisites;

public interface RequisitesRepository extends JpaRepository<Requisites, Long> {
    Requisites findByPayeeAccount(Account payeeAccount);
}
