package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.ModerationRequestDto;
import ru.shchff.superevent_backend.dto.ModerationRequestUpdateDto;
import ru.shchff.superevent_backend.services.ModerationService;

import java.util.List;

@RestController
@RequestMapping("/moderation")
@RequiredArgsConstructor
@Tag(name = "Модераторские методы")
public class ModerationController {
    private final ModerationService moderationService;

    @GetMapping
    public List<ModerationRequestDto> getAllModerationRequests() {
        return moderationService.getAllModerationRequests();
    }

    @GetMapping("/{id}")
    public ModerationRequestDto getModerationRequestById(@PathVariable Long id) {
        return moderationService.getModerationRequestById(id);
    }

    @PatchMapping("/{id}")
    public ModerationRequestDto updateModerationRequest(
            @PathVariable Long id,
            @RequestBody ModerationRequestUpdateDto request) {
        return moderationService.updateModerationRequest(id, request);
    }
}