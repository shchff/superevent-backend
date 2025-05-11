package ru.shchff.superevent_backend.dto;

import lombok.Data;

@Data
public class AddToFavoriteRequest
{
    private Long userId;
    private Long venueId;
}
