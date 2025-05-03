package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.services.VenueService;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
@Tag(name = "Авторизация")
public class VenueController
{
    private final VenueService venueService;

    @Operation(summary = "Получение всех площадок")
    @GetMapping
    public List<Venue> getVenues()
    {
        return venueService.getVenues();
    }
}
