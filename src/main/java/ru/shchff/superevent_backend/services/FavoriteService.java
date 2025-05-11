package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.AddToFavoriteRequest;
import ru.shchff.superevent_backend.dto.FavoriteDto;
import ru.shchff.superevent_backend.entities.Favorite;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.repositories.FavoriteRepository;
import ru.shchff.superevent_backend.repositories.UserRepository;
import ru.shchff.superevent_backend.repositories.VenueRepository;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService
{
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void addToFavorite(AddToFavoriteRequest request)
    {
        Favorite favorite = new Favorite();

        favorite.setCreatedAt(LocalDateTime.now());

        Long userId = request.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        favorite.setUser(user);

        Long venueId = request.getVenueId();
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new VenueNotFoundException(venueId));
        favorite.setVenue(venue);

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteFavorite(Long id)
    {
        favoriteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<FavoriteDto> getAllUsersFavorites(Long userId)
    {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        return favorites.stream()
                .map(this::convertToDto)
                .toList();
    }

    private FavoriteDto convertToDto(Favorite favorite)
    {
        FavoriteDto favoriteDto = new FavoriteDto();

        favoriteDto.setId(favorite.getId());
        favoriteDto.setVenueId(favorite.getVenue().getId());
        favoriteDto.setUserId(favorite.getUser().getId());
        favoriteDto.setCreatedAt(favorite.getCreatedAt());

        return favoriteDto;
    }
}
