package ru.kpfu.itis.lobanov.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.lobanov.data.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
