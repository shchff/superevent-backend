package ru.shchff.superevent_backend.util;

public class ModerationRequestNotFoundException extends RuntimeException
{
    public ModerationRequestNotFoundException(Long id) {
        super("Запрос на модерацию с id " + id + "не найден!");
    }
}
