package ru.shchff.superevent_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
public class Venue
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "registration_doc_url")
    private String registrationDocUrl;

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String string;
    @Column(nullable = false)
    private String building;
    @Column(nullable = false)
    private String contacts;

    @Column(name = "working_hours")
    private String workingHours;

    private BigDecimal price;
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VenueStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "venue_tags",
            joinColumns = @JoinColumn(name = "venue_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VenueImage> images = new ArrayList<>();
}
