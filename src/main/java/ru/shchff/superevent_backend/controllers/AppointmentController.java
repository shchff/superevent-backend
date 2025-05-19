package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.AppointmentDto;
import ru.shchff.superevent_backend.services.AppointmentService;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Бронирование")
public class AppointmentController
{
    private final AppointmentService appointmentService;

    @GetMapping("/upcoming/user/{userId}")
    @Operation(summary = "Получить записи на площадку с сегодняшней даты")
    @ApiResponse(responseCode = "200", description = "Список записей получен")
    public List<AppointmentDto> getUpcomingForUser(@PathVariable Long userId)
    {
        return appointmentService.findUpcomingForUser(userId);
    }


    @Operation(summary = "Получить записи на площадку с сегодняшней даты")
    @ApiResponse(responseCode = "200", description = "Список записей получен")
    @GetMapping("/venue/{venueId}")
    public List<AppointmentDto> getAppointmentsByVenue(@PathVariable Long venueId)
    {
        return appointmentService.getAppointmentsByVenueFromToday(venueId);
    }

    @Operation(summary = "Бронирование площадки")
    @PostMapping
    public AppointmentDto makeAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        return appointmentService.createAppointment(appointmentDto);
    }

    @Operation(summary = "Удаление брони")
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id)
    {
        appointmentService.deleteAppointment(id);
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
