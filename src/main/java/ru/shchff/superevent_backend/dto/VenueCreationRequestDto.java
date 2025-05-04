package ru.shchff.superevent_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VenueCreationRequestDto
{
    @NotNull(message = "У площадки должен быть представитель")
    private long ownerId;
    private String registrationCertificatePath;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    private String description;
    private String city;
    private String street;
    private String building;
    private String workingHours;
    private int capacity;
    private BigDecimal price;
    private long categoryId;
    private List<Long> tagIds;
    private List<String> imagesPaths;
}
