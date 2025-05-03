package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.services.VenueService;
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
    @ApiResponse(responseCode = "200", description = "Площадки успешно получена")
    @GetMapping
    public List<Venue> getVenues()
    {
        return venueService.getVenues();
    }

    @Operation(summary = "Получение площадки", description = "Получение площадки по её id")
    @ApiResponse(responseCode = "200", description = "Площадка успешно получена")
    @ApiResponse(responseCode = "404", description = "Площадка с данным id не найдена")
    @GetMapping("/{id}")
    public Venue getVenueById(
            @Parameter(description = "Id площадки", example = "1") @PathVariable("id") long id
    )
    {
        return venueService.findVenue(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(VenueNotFoundException e)
    {
        ErrorResponse response = new ErrorResponse(
                "Площадка с данным id не найдена!",
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    

}
