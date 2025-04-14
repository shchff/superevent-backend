package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.Venue;
import ru.shchff.superevent_backend.entity.VenueStatus;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long>
{
    List<Venue> findByStatus(VenueStatus status);
    List<Venue> findByCategory_Id(Long categoryId);
    List<Venue> findByUser_Id(Long userId);
    List<Venue> findByOwnerId(Long userId);
}
