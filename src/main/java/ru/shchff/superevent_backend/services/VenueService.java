package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.*;
import ru.shchff.superevent_backend.entities.*;
import ru.shchff.superevent_backend.repositories.*;
import ru.shchff.superevent_backend.util.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<VenueDto> getVenues()
    {
        return venueRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VenueDto findVenue(long id)
    {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));
        return convertToDto(venue);
    }

    @Transactional
    public VenueDto createVenue(VenueCreationRequestDto request)
    {
        Venue venue = modelMapper.map(request, Venue.class);

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(request.getOwnerId()));
        venue.setOwner(owner);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));
        venue.setCategory(category);

        venue.setStatus(VenueStatus.PENDING);
        venue.setCreatedAt(LocalDateTime.now());

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            venue.setTags(tags);
        }

        if (request.getImagesPaths() != null && !request.getImagesPaths().isEmpty()) {
            List<VenueImage> images = request.getImagesPaths().stream()
                    .map(path -> new VenueImage(path, venue))
                    .collect(Collectors.toList());
            venue.setImages(images);
        }

        Venue savedVenue = venueRepository.save(venue);

        ModerationRequest moderationRequest = new ModerationRequest(savedVenue);
        moderationRequestRepository.save(moderationRequest);

        return convertToDto(savedVenue);
    }

    @Transactional
    public VenueDto updateVenue(long id, VenueUpdateDto updateDto, Long ownerId) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));

        if (!venue.getOwner().getId().equals(ownerId)) {
            throw new AccessDeniedException("Только владелец может редактировать площадку");
        }

        updateVenueFields(venue, updateDto);
        Venue updatedVenue = venueRepository.save(venue);

        ModerationRequest moderationRequest = new ModerationRequest(updatedVenue);
        moderationRequestRepository.save(moderationRequest);

        return convertToDto(updatedVenue);
    }

    @Transactional
    public VenueDto updateVenueTags(long venueId, List<Long> tagIds, Long ownerId)
    {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new VenueNotFoundException(venueId));

        if (!venue.getOwner().getId().equals(ownerId))
        {
            throw new AccessDeniedException("Только владелец может изменять теги");
        }

        venue.getTags().clear();
        if (tagIds != null && !tagIds.isEmpty())
        {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tagIds));
            venue.setTags(tags);
        }

        return convertToDto(venueRepository.save(venue));
    }

    @Transactional(readOnly = true)
    public List<VenueDto> getVenuesByOwner(Long ownerId)
    {
        return venueRepository.findAllByOwnerId(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VenueDto convertToDto(Venue venue)
    {
        VenueDto dto = modelMapper.map(venue, VenueDto.class);
        dto.setOwnerId(venue.getOwner().getId());
        dto.setCategoryId(venue.getCategory().getId());
        dto.setTagIds(venue.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()));
        dto.setImagesPaths(venue.getImages().stream()
                .map(VenueImage::getImagePath)
                .collect(Collectors.toList()));
        return dto;
    }

    private void updateVenueFields(Venue venue, VenueUpdateDto updateDto)
    {
        if (updateDto.getName() != null) venue.setName(updateDto.getName());
        if (updateDto.getDescription() != null) venue.setDescription(updateDto.getDescription());
        if (updateDto.getCity() != null) venue.setCity(updateDto.getCity());
        if (updateDto.getStreet() != null) venue.setStreet(updateDto.getStreet());
        if (updateDto.getBuilding() != null) venue.setBuilding(updateDto.getBuilding());
        if (updateDto.getWorkingHours() != null) venue.setWorkingHours(updateDto.getWorkingHours());
        if (updateDto.getPrice() != null) venue.setPrice(updateDto.getPrice());
        if (updateDto.getCapacity() != null) venue.setCapacity(updateDto.getCapacity());
        if (updateDto.getRegistrationCertificatePath() != null)

        {
            venue.setRegistrationCertificatePath(updateDto.getRegistrationCertificatePath());
        }
        if (updateDto.getCategoryId() != null)
        {
            Category category = categoryRepository.findById(updateDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(updateDto.getCategoryId()));
            venue.setCategory(category);
        }
    }
}