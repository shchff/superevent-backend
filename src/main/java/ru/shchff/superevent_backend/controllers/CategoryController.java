package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.entities.Category;
import ru.shchff.superevent_backend.services.CategoryService;
import ru.shchff.superevent_backend.util.CategoryNotFoundException;
import ru.shchff.superevent_backend.util.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Категории")
public class CategoryController
{
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Получение всех категорий")
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение категории по id")
    public Category getCategoryById(@PathVariable Long id)
    {
        return categoryService.getCategoryById(id);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CategoryNotFoundException e)
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
