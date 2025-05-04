package ru.shchff.superevent_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.shchff.superevent_backend.entities.ModerationStatus;

@Data
public class ModerationRequestUpdateDto {
    private String moderatorComment;
    @NotNull
    private ModerationStatus status;
}