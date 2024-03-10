package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lobanov.data.entities.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
}
