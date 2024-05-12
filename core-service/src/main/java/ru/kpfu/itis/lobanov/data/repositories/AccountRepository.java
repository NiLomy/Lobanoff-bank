package ru.kpfu.itis.lobanov.data.repositories;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.data.entities.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.readOnly", value = "true"),
            @QueryHint(name = "javax.persistence.query.timeout", value = "5000")
    })
    @Query("select a from Account a left join a.cards c where a.owner = (select u from User u where u.id = :ownerId) group by a.id order by count(c) desc, a.id asc")
    List<Account> findAllByOwnerId(Long ownerId);

    @Query("select a from Account a where a.owner = (select u from User u where u.id = :ownerId) and (select count(Card) from Account) != 0")
    List<Account> findAllCardsByOwnerId(Long ownerId);

    @Query("select a from Account a where a.type.name = :accountType")
    List<Account> findAllByAccountType(String accountType);

    List<Account> findAllByType(AccountType type);

    @Query("select a from Account a left join a.cards c where c.number = :card")
    Optional<Account> findByCardNumber(String card);

    @Query("select a from Account a")
    Optional<Account> findByContractNumber(String contractNumber);

    @Query("select a from Account a where a.owner.id = :userId and a.main = true")
    Optional<Account> findUserMainAccount(Long userId);

    @Modifying
    @Query("update Account set name = :name where id = :id")
    void updateNameById(Long id, String name);

    @Modifying
    @Query("update Account set deposit = :deposit where id = :id")
    void updateDepositById(Long id, Long deposit);
}
