package ru.shchff.superevent_backend.dto;

import lombok.Data;
import ru.shchff.superevent_backend.entities.ModerationStatus;

import java.time.LocalDateTime;

@Data
public class ModerationRequestDto {
    private Long id;
    private Long venueId;
    private LocalDateTime submittedAt;
    private String moderatorComment;
    private ModerationStatus status;
}