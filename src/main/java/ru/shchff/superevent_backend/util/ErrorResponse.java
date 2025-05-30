package ru.shchff.superevent_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse
{
    private String message;
    private long timestamp;
}
