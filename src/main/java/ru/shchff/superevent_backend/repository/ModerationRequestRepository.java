package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.ModerationRequest;
import ru.shchff.superevent_backend.entity.VenueStatus;

import java.util.List;
import java.util.Optional;

public interface ModerationRequestRepository extends JpaRepository<ModerationRequest, Long>
{
    List<ModerationRequest> findByStatus(VenueStatus status);
    Optional<ModerationRequest> findByVenue_Id(Long venueId);
}
