package ru.shchff.superevent_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageRatingDto
{
    private Long id;
    private Double avgRating;
}
