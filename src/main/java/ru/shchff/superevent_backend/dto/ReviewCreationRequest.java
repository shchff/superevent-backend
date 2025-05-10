package ru.shchff.superevent_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreationRequest
{
    @NotNull(message = "У отзыва должен быть автор")
    private Long userId;
    @NotNull(message = "У отзыва должна быть площадка")
    private Long venueId;
    @Min(value = 1, message = "Рейтинг должен быть не менее 1")
    @Max(value = 5, message = "Рейтинг должен быть не более 5")
    @NotNull(message = "У отзыва должен быть рейтинг")
    private int rating;
    private String comment;
}
