package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.dto.UserUpdateDto;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.services.UserService;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController
{
    private final UserService userService;

    @Operation(summary = "Обновление информации о пользователе")
    @ApiResponse(responseCode = "200", description = "Информация обновлена")
    @ApiResponse(responseCode = "403", description = "Нет прав на обновление")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @PatchMapping("/{id}")
    // @PreAuthorize("#id == principal.id or hasRole('ADMIN')") // Только сам пользователь или админ
    public User updateUser(
            @Parameter(description = "ID пользователя") @PathVariable long id,
            @RequestBody UserUpdateDto updateDto) {
        return userService.updateUser(id, updateDto);
    }

    @Operation(summary = "Получение пользователя по id")
    @ApiResponse(responseCode = "200", description = "Пользоавтель получен")
    @ApiResponse(responseCode = "404", description = "Пользователь с данным id не найден")
    @GetMapping("/{id}")
    public User getUser(
            @Parameter(description = "Id пользователя", example = "1") @PathVariable("id") long id)
    {
        return userService.findOne(id);
    }

    @Operation(summary = "Получение всех пользователей")
    @ApiResponse(responseCode = "200", description = "Пользоавтели получены")
    @GetMapping()
    public List<User> getUsers()
    {
        return userService.getAllUsers();
    }

    @Operation(summary = "Получение пользователя по email")
    @ApiResponse(responseCode = "200", description = "Пользоавтель получен")
    @ApiResponse(responseCode = "404", description = "Пользователь с данным email не найден")
    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable("email") String email)
    {
        return userService.findByEmail(email);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e)
    {
        ErrorResponse response = new ErrorResponse(
                "Юзер с данным id не найден!",
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
