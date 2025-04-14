package ru.shchff.superevent_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.shchff.superevent_backend.entity.Review;
import ru.shchff.superevent_backend.entity.User;
import ru.shchff.superevent_backend.entity.Venue;
import ru.shchff.superevent_backend.repository.ReviewRepository;
import ru.shchff.superevent_backend.repository.UserRepository;
import ru.shchff.superevent_backend.repository.VenueRepository;
import ru.shchff.superevent_backend.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService
{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, VenueRepository venueRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Review addReview(Long userId, Long venueId, String comment, int rating) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Venue> venue = venueRepository.findById(venueId);

        if (user.isPresent() && venue.isPresent()) {
            Review review = Review.builder()
                    .user(user.get())
                    .venue(venue.get())
                    .rating(rating)
                    .comment(comment)
                    .createdAt(LocalDateTime.now())  // автоматически устанавливаем время
                    .build();
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("User or Venue not found");
        }
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> getReviewsByVenueId(Long venueId) {
        return reviewRepository.findByVenueId(venueId);
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}
