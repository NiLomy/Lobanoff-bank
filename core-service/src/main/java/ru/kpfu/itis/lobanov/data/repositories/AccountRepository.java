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

    @Query("select a from Account a where a.owner.id = :ownerId order by a.main desc, a.id asc")
//    @Query(value = "select * from accounts a left join cards c on a.id = c.account_id left join transactions t on a.id = t.from left join transactions tr on a.id = tr.to where a.owner_id = :ownerId group by a.id, a.main, c.id, t.id, tr.id order by a.main desc, a.id asc", nativeQuery = true)
    List<Account> findAllByOwnerId(Long ownerId);

    @Query(value = "select * from accounts a where a.owner_id = :ownerId and (select count(*) from cards c where c.account_id = a.id) != 0", nativeQuery = true)
    List<Account> findAllCardsByOwnerId(Long ownerId);

    @Query("select a from Account a where a.type.name = :accountType")
    List<Account> findAllByAccountType(String accountType);

    List<Account> findAllByType(AccountType type);

    @Query("select a from Account a left join fetch a.cards c where c.number = :card")
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
