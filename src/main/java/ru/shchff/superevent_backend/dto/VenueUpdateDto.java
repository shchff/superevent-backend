package ru.shchff.superevent_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class VenueUpdateDto {
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String name;

    @Size(max = 2000, message = "Описание не должно превышать 2000 символов")
    private String description;

    @Size(max = 100, message = "Город не должен превышать 100 символов")
    private String city;

    @Size(max = 100, message = "Улица не должна превышать 100 символов")
    private String street;

    @Size(max = 20, message = "Здание не должно превышать 20 символов")
    private String building;

    @Size(max = 100, message = "Режим работы не должен превышать 100 символов")
    private String workingHours;

    @DecimalMin(value = "0.0", message = "Цена не может быть отрицательной")
    private BigDecimal price;

    @Min(value = 1, message = "Вместимость должна быть не менее 1")
    private Integer capacity;

    private Long categoryId;

    @Size(max = 255, message = "Сертификат не должен превышать 255 символов")
    private String registrationCertificatePath;
}