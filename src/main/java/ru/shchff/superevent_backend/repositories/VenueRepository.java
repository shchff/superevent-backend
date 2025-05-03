package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long>
{
}
