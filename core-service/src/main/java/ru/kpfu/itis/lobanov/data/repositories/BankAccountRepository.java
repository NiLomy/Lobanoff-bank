package ru.kpfu.itis.lobanov.data.repositories;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.readOnly", value = "true"),
            @QueryHint(name = "javax.persistence.query.timeout", value = "5000")
    })
    @Query("select b from BankAccount b left join b.cards c where b.owner = (select u from User u where u.id = :ownerId) group by b.id order by count(c) desc, b.id asc")
    List<BankAccount> findAllByOwnerId(Long ownerId);

    @Query("select b from BankAccount b where b.owner = (select u from User u where u.id = :ownerId) and (select count(Card) from BankAccount) != 0")
    List<BankAccount> findAllCardsByOwnerId(Long ownerId);

    @Query("select b from BankAccount b left join b.cards c where c.number = :card")
    Optional<BankAccount> findByCardNumber(String card);

    @Query("select b from BankAccount b")
    Optional<BankAccount> findByContractNumber(String contractNumber);

    @Query("select b from BankAccount b where b.owner.id = :userId and b.main = true")
    Optional<BankAccount> findUserMainAccount(Long userId);

    @Modifying
    @Query("update BankAccount set name = :name where id = :id")
    void updateNameById(Long id, String name);

    @Modifying
    @Query("update BankAccount set deposit = :deposit where id = :id")
    void updateDepositById(Long id, Long deposit);
}
