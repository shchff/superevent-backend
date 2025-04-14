package ru.shchff.superevent_backend.service;

import ru.shchff.superevent_backend.entity.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Long userId, Long venueId, String comment, int rating); // Добавление отзыва
    void deleteReview(Long reviewId);                                   // Удаление отзыва
    List<Review> getReviewsByVenueId(Long venueId);                     // Получение всех отзывов по площадке
    List<Review> getReviewsByUserId(Long userId);                       // Получение всех отзывов пользователя
}