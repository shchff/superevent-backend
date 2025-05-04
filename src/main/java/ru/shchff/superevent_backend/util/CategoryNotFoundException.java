package ru.shchff.superevent_backend.util;

public class CategoryNotFoundException extends RuntimeException
{
    public CategoryNotFoundException(long id)
    {
        super("Категории с id " + id + " не найдено!");
    }
}
