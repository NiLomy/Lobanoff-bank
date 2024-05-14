package ru.kpfu.itis.gateway.lobanov.gatewayservice.repositories;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.readOnly", value = "true"),
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.comment", value = "Retrieve client by his email")
    })
    User findByEmail(String email);
}