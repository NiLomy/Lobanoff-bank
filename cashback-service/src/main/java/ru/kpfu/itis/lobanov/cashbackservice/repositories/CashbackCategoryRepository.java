package ru.kpfu.itis.lobanov.cashbackservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.cashbackservice.entities.CashbackCategory;

import java.util.Optional;

@Repository
public interface CashbackCategoryRepository extends JpaRepository<CashbackCategory, Long> {
    Optional<CashbackCategory> findByCategoryId(Long categoryId);
}
