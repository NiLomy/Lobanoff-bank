package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("select t from RefreshToken t where t.user.id = :id")
    Optional<RefreshToken> findByUserId(Long id);

    @Query("select case when exists (select t from RefreshToken t where t.user.id = :id) then true else false end")
    boolean existsByUserId(Long id);

    @Modifying
    @Query("update RefreshToken set token = :token where user.id = :id")
    void updateToken(Long id, String token);
}

