package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.CardInfo;

import java.util.Optional;

@Repository
public interface BankInfoRepository extends JpaRepository<CardInfo, Long> {
    @Query("select b.bin from CardInfo b where b.issuer = :issuer")
    Optional<String> findBinByIssuer(String issuer);
}
