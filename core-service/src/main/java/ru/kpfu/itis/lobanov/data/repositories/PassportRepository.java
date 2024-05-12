package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.Passport;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Passport findBySeriesAndNumber(Short series, Integer number);

    boolean existsBySeriesAndNumber(Short series, Integer number);
}
