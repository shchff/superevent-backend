package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>
{
    List<Favorite> findByUserId(Long userId);
    void deleteByUserIdAndVenueId(Long userId, Long venueId);
}
