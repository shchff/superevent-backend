package ru.shchff.superevent_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto
{
    private Long id;
    private Long userId;
    private Long venueId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
