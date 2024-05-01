package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lobanov.data.entities.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
