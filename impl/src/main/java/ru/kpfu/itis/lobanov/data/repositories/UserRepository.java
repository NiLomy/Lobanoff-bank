package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.lobanov.data.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIsDeletedIsFalse();

    User findByEmail(String email);

    User findByPhone(String phone);

    @Query("select u from User u left join Card c on u = c.owner where c.number = :card")
    User findByCard(String card);

    @Modifying
    @Query("update User set state = :state where id = :id")
    User updateStateById(Long id, String state);
}