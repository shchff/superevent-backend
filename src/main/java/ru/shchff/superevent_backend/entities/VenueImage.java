package ru.shchff.superevent_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "venue_images")
@Getter
@Setter
@NoArgsConstructor
public class VenueImage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    public VenueImage(String imagePath, Venue venue)
    {
        this.imagePath = imagePath;
        this.venue = venue;
    }
}
