package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.BanRequest;
import ru.shchff.superevent_backend.dto.BanResultDto;
import ru.shchff.superevent_backend.dto.ModerationRequestDto;
import ru.shchff.superevent_backend.dto.ModerationRequestUpdateDto;
import ru.shchff.superevent_backend.entities.*;
import ru.shchff.superevent_backend.repositories.ModerationRequestRepository;
import ru.shchff.superevent_backend.repositories.UserRepository;
import ru.shchff.superevent_backend.repositories.VenueRepository;
import ru.shchff.superevent_backend.util.ModerationRequestNotFoundException;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationService {
    private final ModerationRequestRepository moderationRequestRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<ModerationRequestDto> getAllModerationRequests() {
        return moderationRequestRepository.findAll().stream()
                .map(request -> modelMapper.map(request, ModerationRequestDto.class))
                .toList();
    }

    public ModerationRequestDto getModerationRequestById(Long id) {
        ModerationRequest request = moderationRequestRepository.findById(id)
                .orElseThrow(() -> new ModerationRequestNotFoundException(id));
        return modelMapper.map(request, ModerationRequestDto.class);
    }

    @Transactional
    public ModerationRequestDto updateModerationRequest(Long id, ModerationRequestUpdateDto request) {
        ModerationRequest moderationRequest = moderationRequestRepository.findById(id)
                .orElseThrow(() -> new ModerationRequestNotFoundException(id));

        if (request.getModeratorComment() != null) {
            moderationRequest.setModeratorComment(request.getModeratorComment());
        }

        if (request.getStatus() != null) {
            moderationRequest.setStatus(request.getStatus());
            Venue venue = moderationRequest.getVenue();
            switch (request.getStatus()) {
                case APPROVED -> venue.setStatus(VenueStatus.APPROVED);
                case REJECTED -> venue.setStatus(VenueStatus.REJECTED);
                case PENDING -> venue.setStatus(VenueStatus.PENDING);
            }
            venueRepository.save(venue);
        }

        ModerationRequest updatedRequest = moderationRequestRepository.save(moderationRequest);
        return modelMapper.map(updatedRequest, ModerationRequestDto.class);
    }

    @Transactional
    public BanResultDto toggleVenueBan(Long venueId, BanRequest request) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new VenueNotFoundException(venueId));

        venue.setStatus(request.isBanned() ? VenueStatus.BLOCKED : VenueStatus.APPROVED);
        venueRepository.save(venue);

        return BanResultDto.builder()
                .id(venueId)
                .banned(request.isBanned())
                .build();
    }

    @Transactional
    public BanResultDto toggleUserBan(Long userId, BanRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setStatus(request.isBanned() ? UserStatus.BLOCKED : UserStatus.ACTIVE);

        if (request.isBanned()) {
            venueRepository.findAllByOwnerId(userId)
                    .forEach(venue -> {
                        venue.setStatus(VenueStatus.BLOCKED);
                        venueRepository.save(venue);
                    });
        }

        userRepository.save(user);

        return BanResultDto.builder()
                .id(userId)
                .banned(request.isBanned())
                .build();
    }
}
