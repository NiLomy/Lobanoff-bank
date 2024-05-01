package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.TransactionMethod;

import java.util.Optional;

@Repository
public interface TransactionMethodRepository extends JpaRepository<TransactionMethod, Long> {
    Optional<TransactionMethod> findByName(String name);
}
