package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
    Optional<Category> findByName(String name);
}
