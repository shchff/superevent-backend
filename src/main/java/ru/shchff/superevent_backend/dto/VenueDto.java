package ru.shchff.superevent_backend.dto;

import lombok.Data;
import ru.shchff.superevent_backend.entities.VenueStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class VenueDto {
    private Long id;
    private Long ownerId;
    private String registrationCertificatePath;
    private String name;
    private String description;
    private String city;
    private String street;
    private String building;
    private String workingHours;
    private BigDecimal price;
    private int capacity;
    private Long categoryId; // вместо объекта Category
    private VenueStatus status;
    private LocalDateTime createdAt;
    private Set<Long> tagIds; // вместо Set<Tag>
    private List<String> imagesPaths; // вместо List<VenueImage>
}