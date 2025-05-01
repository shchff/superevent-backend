package ru.shchff.superevent_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Для представителей площадки
    @Column(name = "phone_number")
    private String phoneNumber;

    // Для представителей площадки (PDF/фото)
    @Column(name = "registration_certificate_path")
    private String registrationCertificatePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(name= "created_ad", nullable = false)
    private LocalDateTime createdAt;
}
