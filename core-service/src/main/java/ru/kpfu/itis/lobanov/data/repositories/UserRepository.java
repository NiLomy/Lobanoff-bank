package ru.kpfu.itis.lobanov.data.repositories;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.kpfu.itis.lobanov.data.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedIsFalse();

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.readOnly", value = "true"),
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value = "USE"),
            @QueryHint(name = "jakarta.persistence.cache.storeMode", value = "USE"),
            @QueryHint(name = "org.hibernate.comment", value = "Retrieve client by his email")
    })
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByVerificationCode(String verificationCode);

    @Query("select u from User u left join fetch Card c on u = c.owner where c.number = :card")
    User findByCard(String card);

    @Modifying
    @Query("update User set state = :state where id = :id")
    User updateStateById(Long id, String state);

    boolean existsByEmail(String email);
}