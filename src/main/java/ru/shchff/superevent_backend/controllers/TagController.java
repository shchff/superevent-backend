package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.entities.Tag;
import ru.shchff.superevent_backend.services.TagService;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.TagNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Теги")
public class TagController
{

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "Получение всех тегов")
    public List<Tag> getAllTags()
    {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение тега по id")
    public Tag getTagById(@PathVariable Long id)
    {
        return tagService.getTagById(id);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TagNotFoundException e)
    {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, HttpStatus status)
    {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(status).body(response);
    }
}
