package ru.shchff.superevent_backend.controllers;

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
public class UserController
{
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id)
    {
        return userService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e)
    {
        UserErrorResponse response = new UserErrorResponse(
                "Person with this id was not found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
