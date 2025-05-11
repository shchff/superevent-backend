package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.AddToFavoriteRequest;
import ru.shchff.superevent_backend.dto.FavoriteDto;
import ru.shchff.superevent_backend.services.FavoriteService;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
@Tag(name = "Избранное")
public class FavoriteController
{
    private final FavoriteService favoriteService;

    @PostMapping
    @Operation(summary = "Добавление площадки в избранное")
    public void addReview(@RequestBody AddToFavoriteRequest request)
    {
        favoriteService.addToFavorite(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление избранного")
    public void deleteReview(@PathVariable Long id)
    {
        favoriteService.deleteFavorite(id);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Получение избранных площадок пользователя")
    public List<FavoriteDto> getAllUsersReviews(@PathVariable Long id)
    {
        return favoriteService.getAllUsersFavorites(id);
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
