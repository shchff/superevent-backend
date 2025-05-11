package ru.shchff.superevent_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteDto
{
    private Long id;
    private Long userId;
    private Long venueId;
    private LocalDateTime createdAt;
}
