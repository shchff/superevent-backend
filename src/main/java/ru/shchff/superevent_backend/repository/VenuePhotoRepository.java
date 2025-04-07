package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.VenuePhoto;

import java.util.List;

public interface VenuePhotoRepository extends JpaRepository<VenuePhoto, Long>
{
    List<VenuePhoto> findByVenue_Id(Long venueId);
}
