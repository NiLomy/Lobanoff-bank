package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.BankAccountType;
import ru.kpfu.itis.lobanov.data.entities.BankInfo;

import java.util.Optional;

@Repository
public interface BankAccountTypeRepository extends JpaRepository<BankAccountType, Long> {
}
