package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.VenueCreationRequestDto;
import ru.shchff.superevent_backend.dto.VenueDto;
import ru.shchff.superevent_backend.dto.VenueUpdateDto;
import ru.shchff.superevent_backend.services.VenueService;
import ru.shchff.superevent_backend.util.CategoryNotFoundException;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
@Tag(name = "Площадки")
public class VenueController
{
    private final VenueService venueService;

    @Operation(summary = "Получение всех площадок")
    @ApiResponse(responseCode = "200", description = "Площадки успешно получены")
    @GetMapping
    public List<VenueDto> getVenues()
    {
        return venueService.getVenues();
    }

    @Operation(summary = "Получение площадки по id")
    @ApiResponse(responseCode = "200", description = "Площадка успешно получена")
    @ApiResponse(responseCode = "404", description = "Площадка не найдена")
    @GetMapping("/{id}")
    public VenueDto getVenueById(@PathVariable long id)
    {
        return venueService.findVenue(id);
    }

    @Operation(summary = "Создание площадки")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueDto createVenue(@RequestBody VenueCreationRequestDto request) {
        return venueService.createVenue(request);
    }

    @Operation(summary = "Обновление информации о площадке")
    @ApiResponse(responseCode = "200", description = "Информация обновлена")
    @PatchMapping("/{id}")
    public VenueDto updateVenue(
            @PathVariable long id,
            @RequestBody VenueUpdateDto updateDto,
            @RequestParam Long ownerId) {
        return venueService.updateVenue(id, updateDto, ownerId);
    }


    @Operation(summary = "Обновление тегов площадки")
    @ApiResponse(responseCode = "200", description = "Теги обновлены")
    @PutMapping("/{id}/tags")
    public VenueDto updateVenueTags(
            @PathVariable long id,
            @RequestBody List<Long> tagIds,
            @RequestParam Long ownerId) {
        return venueService.updateVenueTags(id, tagIds, ownerId);
    }

    @Operation(summary = "Получение площадок владельца")
    @ApiResponse(responseCode = "200", description = "Список площадок получен")
    @GetMapping("/owner/{ownerId}")
    public List<VenueDto> getVenuesByOwner(@PathVariable Long ownerId) {
        return venueService.getVenuesByOwner(ownerId);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(VenueNotFoundException e) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CategoryNotFoundException e) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException e) {
        return createErrorResponse(e, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, HttpStatus status) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(status).body(response);
    }
}
