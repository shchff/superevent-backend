package ru.shchff.superevent_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserErrorResponse
{
    private String message;
    private long timestamp;
}
