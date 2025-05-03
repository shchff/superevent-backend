package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.repositories.VenueRepository;

import java.util.List;

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
}
