package ru.shchff.superevent_backend.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BanResultDto {
    private Long id;
    private boolean banned;
}
