package ru.shchff.superevent_backend.service;

import ru.shchff.superevent_backend.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite addToFavorites(Long userId, Long venueId);     // Добавление в избранное
    void removeFromFavorites(Long userId, Long venueId);    // Удаление из избранного
    List<Favorite> getFavoritesByUserId(Long userId);       // Получение избранных площадок
}
