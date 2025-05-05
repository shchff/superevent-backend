package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Venue;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long>
{
    List<Venue> findAllByOwnerId(Long ownerId);
}
