package ru.shchff.superevent_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "moderation_requests")
@Getter
@Setter
@NoArgsConstructor
public class ModerationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @Lob
    private String moderatorComment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModerationStatus status;
}
