package ru.shchff.superevent_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venue_photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenuePhoto {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "photo_url")
    private String photoUrl;
}

