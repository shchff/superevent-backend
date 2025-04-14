package ru.shchff.superevent_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shchff.superevent_backend.entity.Favorite;
import ru.shchff.superevent_backend.entity.User;
import ru.shchff.superevent_backend.entity.Venue;
import ru.shchff.superevent_backend.repository.FavoriteRepository;
import ru.shchff.superevent_backend.repository.UserRepository;
import ru.shchff.superevent_backend.repository.VenueRepository;
import ru.shchff.superevent_backend.service.FavoriteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UserRepository userRepository, VenueRepository venueRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Favorite addToFavorites(Long userId, Long venueId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Venue> venue = venueRepository.findById(venueId);

        if (user.isPresent() && venue.isPresent()) {
            Favorite favorite = Favorite.builder()
                    .user(user.get())
                    .venue(venue.get())
                    .addedAt(LocalDateTime.now())  // автоматическое добавление времени
                    .build();
            return favoriteRepository.save(favorite);
        } else {
            throw new RuntimeException("User or Venue not found");
        }
    }

    @Override
    public void removeFromFavorites(Long userId, Long venueId) {
        favoriteRepository.deleteByUserIdAndVenueId(userId, venueId);
    }

    @Override
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}