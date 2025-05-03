package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.repositories.VenueRepository;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VenueService
{
    private final VenueRepository venueRepository;

    @Transactional(readOnly = true)
    public List<Venue> getVenues()
    {
        return venueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Venue findVenue(long id)
    {
        Optional<Venue> foundVenue = venueRepository.findById(id);
        return foundVenue.orElseThrow(VenueNotFoundException::new);
    }
}
