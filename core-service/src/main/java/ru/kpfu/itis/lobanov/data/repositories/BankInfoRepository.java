package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.BankInfo;

@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
}
