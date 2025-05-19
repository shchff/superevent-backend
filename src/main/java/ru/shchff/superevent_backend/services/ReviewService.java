package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.AverageRatingDto;
import ru.shchff.superevent_backend.dto.ReviewCreationRequest;
import ru.shchff.superevent_backend.dto.ReviewDto;
import ru.shchff.superevent_backend.entities.Review;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.repositories.ReviewRepository;
import ru.shchff.superevent_backend.repositories.UserRepository;
import ru.shchff.superevent_backend.repositories.VenueRepository;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService
{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createReview(ReviewCreationRequest request)
    {
        Review review = new Review();

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        Long userId = request.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        review.setUser(user);

        Long venueId = request.getVenueId();
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new VenueNotFoundException(venueId));
        review.setVenue(venue);

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id)
    {
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getAllUsersReviews(Long userId)
    {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Review> reviews = reviewRepository.findByUserId(userId);

        return reviews.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getAllVenuesReviews(Long venueId)
    {
        venueRepository.findById(venueId).orElseThrow(() -> new VenueNotFoundException(venueId));

        List<Review> reviews = reviewRepository.findByVenueId(venueId);

        return reviews.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ReviewDto convertToDto(Review review)
    {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(review.getId());
        reviewDto.setVenueId(review.getVenue().getId());
        reviewDto.setUserId(review.getUser().getId());
        reviewDto.setCreatedAt(review.getCreatedAt());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());

        return reviewDto;
    }

    @Transactional(readOnly = true)
    public AverageRatingDto getAvgVenueRating(Long id)
    {
        List<ReviewDto> reviews = getAllVenuesReviews(id);

        int sum = 0;

        for (ReviewDto review : reviews)
        {
            sum += review.getRating();
        }

        Double avgRating = !reviews.isEmpty() ? sum * 1.0 / reviews.size() : 0;

        return new AverageRatingDto(id, avgRating);
    }
}
