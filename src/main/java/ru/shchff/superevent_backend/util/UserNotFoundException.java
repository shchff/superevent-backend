package ru.shchff.superevent_backend.util;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(long id)
    {
        super("Пользователя с id: " + id + " не найдено!");
    }

    public UserNotFoundException(String email)
    {
        super("Пользователя с email: " + email + " не найдено!");
    }
}
