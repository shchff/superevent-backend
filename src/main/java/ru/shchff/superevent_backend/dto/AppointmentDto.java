package ru.shchff.superevent_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto
{
    private Long id;
    private Long venueId;
    private Long clientId;
    private LocalDate date;
}
