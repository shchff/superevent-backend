package ru.shchff.superevent_backend.util;

public class UserNotCreatedException extends RuntimeException
{
    public UserNotCreatedException(String message)
    {
        super(message);
    }
}
