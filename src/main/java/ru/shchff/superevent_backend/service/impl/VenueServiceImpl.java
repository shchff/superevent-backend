package ru.shchff.superevent_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shchff.superevent_backend.entity.Venue;
import ru.shchff.superevent_backend.entity.VenueStatus;
import ru.shchff.superevent_backend.repository.VenueRepository;
import ru.shchff.superevent_backend.service.VenueService;

import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService
{

    private final VenueRepository venueRepository;

    @Autowired
    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue createVenue(Venue venue) {
        venue.setStatus(VenueStatus.PENDING); // создаётся как "на модерации"
        return venueRepository.save(venue);
    }

    @Override
    public Venue updateVenue(Long id, Venue updatedVenue) {
        return venueRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedVenue.getName());
                    existing.setDescription(updatedVenue.getDescription());
                    existing.setAddress(updatedVenue.getAddress());
                    existing.setCapacity(updatedVenue.getCapacity());
                    existing.setPrice(updatedVenue.getPrice());
                    existing.setCategory(updatedVenue.getCategory());
                    existing.setTags(updatedVenue.getTags());
                    existing.setPhotos(updatedVenue.getPhotos());
                    existing.setWorkingHours(updatedVenue.getWorkingHours());
                    existing.setStatus(VenueStatus.PENDING); // повторная модерация
                    return venueRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Venue not found"));
    }

    @Override
    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    public List<Venue> getAllApprovedVenues() {
        return venueRepository.findByStatus(VenueStatus.APPROVED);
    }

    @Override
    public List<Venue> getVenuesByOwner(Long userId) {
        return venueRepository.findByOwnerId(userId);
    }

    @Override
    public void submitForModeration(Long id) {
        venueRepository.findById(id).ifPresent(venue -> {
            venue.setStatus(VenueStatus.PENDING);
            venueRepository.save(venue);
        });
    }

    @Override
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id); // можно будет заменить на мягкое удаление
    }
}