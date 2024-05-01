package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.BankInfo;

import java.util.Optional;

@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
    @Query("select b.bin from BankInfo b where b.issuer = :issuer")
    Optional<String> findBinByIssuer(String issuer);
}
