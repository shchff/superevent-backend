package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVenueId(Long venueId);  // Получение отзывов для площадки
    List<Review> findByUserId(Long userId);    // Получение всех отзывов пользователя
}
