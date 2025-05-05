package ru.shchff.superevent_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.shchff.superevent_backend.entities.Role;

@Data
public class RegisterRequestDto
{
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String surname;

    @Email(message = "Email должен быть валидным")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен содержать от 8 символов")
    private String password;

    private Role role;
}