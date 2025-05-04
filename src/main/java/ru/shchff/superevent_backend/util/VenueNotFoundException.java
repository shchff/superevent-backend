package ru.shchff.superevent_backend.util;

public class VenueNotFoundException extends RuntimeException
{
    public VenueNotFoundException(long id)
    {
        super("Площадки с id: " + id + " не найдено!");
    }
}
