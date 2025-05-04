package ru.shchff.superevent_backend.util;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(long id)
    {
        super("Пользователя с id: " + id + " не найдено!");
    }
}
