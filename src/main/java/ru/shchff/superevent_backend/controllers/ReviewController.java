package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.ReviewCreationRequest;
import ru.shchff.superevent_backend.dto.ReviewDto;
import ru.shchff.superevent_backend.services.ReviewService;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Отзывы")
public class ReviewController
{
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Добавление отзыва клиента")
    public void addReview(@RequestBody ReviewCreationRequest request)
    {
        reviewService.createReview(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление отзыва")
    public void deleteReview(@PathVariable Long id)
    {
        reviewService.deleteReview(id);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Получение всех отзывов пользователя")
    public List<ReviewDto> getAllUsersReviews(@PathVariable Long userId)
    {
        return reviewService.getAllUsersReviews(userId);
    }

    @GetMapping("/venues/{id}")
    @Operation(summary = "Получение всех отзывов площадки")
    public List<ReviewDto> getAllVenuesReviews(@PathVariable Long userId)
    {
        return reviewService.getAllVenuesReviews(userId);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(VenueNotFoundException e)
    {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e)
    {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, HttpStatus status)
    {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(status).body(response);
    }
}
