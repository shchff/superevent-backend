package ru.shchff.superevent_backend.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String surname;

    @Size(min = 11, max = 20, message = "Номер телефона должен быть от 11 до 20 символов")
    private String phoneNumber;

    private String registrationCertificatePath;
}