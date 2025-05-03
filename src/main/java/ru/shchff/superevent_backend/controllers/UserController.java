package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.services.UserService;
import ru.shchff.superevent_backend.util.UserErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController
{
    private final UserService userService;

    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id)
    {
        return userService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e)
    {
        UserErrorResponse response = new UserErrorResponse(
                "Юзер с данным id не найден!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
