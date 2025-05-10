package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.entities.Category;
import ru.shchff.superevent_backend.repositories.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Категории")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Получение всех категорий")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
