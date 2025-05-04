package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>
{

}
