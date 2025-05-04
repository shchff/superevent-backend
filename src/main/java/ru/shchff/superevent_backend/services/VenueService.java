package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.VenueCreationRequestDto;
import ru.shchff.superevent_backend.entities.*;
import ru.shchff.superevent_backend.repositories.*;
import ru.shchff.superevent_backend.util.CategoryNotFoundException;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VenueService
{
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ModerationRequestRepository moderationRequestRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<Venue> getVenues()
    {
        return venueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Venue findVenue(long id)
    {
        Optional<Venue> foundVenue = venueRepository.findById(id);
        return foundVenue.orElseThrow(() -> new VenueNotFoundException(id));
    }

    @Transactional
    public void createVenue(VenueCreationRequestDto request)
    {
        Venue venue = modelMapper.map(request, Venue.class);

        venue.setOwner(userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(request.getOwnerId())));

        venue.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId())));

        venue.setStatus(VenueStatus.PENDING);
        venue.setCreatedAt(LocalDateTime.now());

        if (request.getTagIds() != null && !request.getTagIds().isEmpty())
        {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            venue.setTags(tags);
        }

        if (request.getImagesPaths() != null && !request.getImagesPaths().isEmpty()) {
            List<VenueImage> images = request.getImagesPaths().stream()
                    .map(path -> {
                        VenueImage image = new VenueImage();
                        image.setImagePath(path);
                        image.setVenue(venue);
                        return image;
                    })
                    .toList();
            venue.setImages(images);
        }

        Venue savedVenue = venueRepository.save(venue);

        ModerationRequest moderationRequest = new ModerationRequest();
        moderationRequest.setVenue(savedVenue);
        moderationRequest.setSubmittedAt(LocalDateTime.now());
        moderationRequest.setStatus(ModerationStatus.PENDING);
        moderationRequest.setModeratorComment(null);

        moderationRequestRepository.save(moderationRequest);
    }
}
