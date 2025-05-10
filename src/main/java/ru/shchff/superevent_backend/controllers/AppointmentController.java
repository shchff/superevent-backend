package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.AppointmentDto;
import ru.shchff.superevent_backend.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Бронирование")
public class AppointmentController
{
    private final AppointmentService appointmentService;

    @Operation(summary = "Получить записи на площадку с сегодняшней даты")
    @ApiResponse(responseCode = "200", description = "Список записей получен")
    @GetMapping("/venue/{venueId}")
    public List<AppointmentDto> getAppointmentsByVenue(@PathVariable Long venueId)
    {
        return appointmentService.getAppointmentsByVenueFromToday(venueId);
    }
}
