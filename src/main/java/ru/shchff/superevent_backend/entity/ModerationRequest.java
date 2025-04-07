package ru.shchff.superevent_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "moderation_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModerationRequest {
    @Id
    private Long id; // = venue_id

    @OneToOne
    @MapsId
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "moderator_comment")
    private String moderatorComment;

    @Enumerated(EnumType.STRING)
    private VenueStatus status;
}
