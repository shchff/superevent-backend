package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>
{
    List<Favorite> findByUser_Id(Long userId);
    Optional<Favorite> findByUser_IdAndVenue_Id(Long userId, Long venueId);
    boolean existsByUser_IdAndVenue_Id(Long userId, Long venueId);
    void deleteByUser_IdAndVenue_Id(Long userId, Long venueId);
}
