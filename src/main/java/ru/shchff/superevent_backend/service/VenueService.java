package ru.shchff.superevent_backend.service;

import ru.shchff.superevent_backend.entity.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    Venue createVenue(Venue venue);                          // Создание
    Venue updateVenue(Long id, Venue updatedVenue);          // Обновление
    Optional<Venue> getVenueById(Long id);                   // По ID
    List<Venue> getAllApprovedVenues();                      // Одобренные площадки
    List<Venue> getVenuesByOwner(Long userId);               // Площадки определённого владельца
    void submitForModeration(Long id);                       // Подача на модерацию
    void deleteVenue(Long id);                               // Удаление (мягкое/жёсткое — обсудим)
}