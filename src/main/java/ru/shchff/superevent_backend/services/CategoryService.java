package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shchff.superevent_backend.entities.Category;
import ru.shchff.superevent_backend.repositories.CategoryRepository;
import ru.shchff.superevent_backend.util.CategoryNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id)
    {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
