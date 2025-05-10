package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.BanRequest;
import ru.shchff.superevent_backend.dto.BanResultDto;
import ru.shchff.superevent_backend.dto.ModerationRequestDto;
import ru.shchff.superevent_backend.dto.ModerationRequestUpdateDto;
import ru.shchff.superevent_backend.services.ModerationService;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/moderation")
@RequiredArgsConstructor
@Tag(name = "Модерация")
public class ModerationController {
    private final ModerationService moderationService;

    @GetMapping
    public List<ModerationRequestDto> getAllModerationRequests()
    {
        return moderationService.getAllModerationRequests();
    }

    @GetMapping("/{id}")
    public ModerationRequestDto getModerationRequestById(@PathVariable Long id)
    {
        return moderationService.getModerationRequestById(id);
    }

    @PatchMapping("/{id}")
    public ModerationRequestDto updateModerationRequest(
            @PathVariable Long id,
            @RequestBody ModerationRequestUpdateDto request)
    {
        return moderationService.updateModerationRequest(id, request);
    }

    @PatchMapping("/venues/{id}/ban")
    @Operation(summary = "Заблокировать/разблокировать площадку")
    public BanResultDto toggleVenueBan(
            @PathVariable Long id,
            @RequestBody BanRequest request)
    {
        return moderationService.toggleVenueBan(id, request);
    }

    @PatchMapping("/users/{id}/ban")
    @Operation(summary = "Заблокировать/разблокировать пользователя")
    public BanResultDto toggleUserBan(
            @PathVariable Long id,
            @RequestBody BanRequest request)
    {
        return moderationService.toggleUserBan(id, request);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e)
    {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(VenueNotFoundException e)
    {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}