package ru.kpfu.itis.gateway.lobanov.gatewayservice.repositories;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.entities.User;

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
    User findByEmail(String email);

    User findByPhone(String phone);

    Optional<User> findByVerificationCode(String verificationCode);

    @Modifying
    @Query("update User set state = :state where id = :id")
    User updateStateById(Long id, String state);

    boolean existsByEmail(String email);
}