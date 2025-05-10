package ru.shchff.superevent_backend.util;

public class TagNotFoundException extends RuntimeException
{
    public TagNotFoundException(Long id)
    {
        super("Тега с id " + id + " не найдено!");
    }
}
