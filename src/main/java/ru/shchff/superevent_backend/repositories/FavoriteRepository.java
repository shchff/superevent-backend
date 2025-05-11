package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>
{
    List<Favorite> findByUserId(Long userId);
}
