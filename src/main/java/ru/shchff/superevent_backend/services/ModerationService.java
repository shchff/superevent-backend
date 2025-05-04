package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.ModerationRequestDto;
import ru.shchff.superevent_backend.dto.ModerationRequestUpdateDto;
import ru.shchff.superevent_backend.entities.*;
import ru.shchff.superevent_backend.repositories.ModerationRequestRepository;
import ru.shchff.superevent_backend.repositories.VenueRepository;
import ru.shchff.superevent_backend.util.ModerationRequestNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationService {
    private final ModerationRequestRepository moderationRequestRepository;
    private final VenueRepository venueRepository;
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

        // Обновляем поля (как было раньше)
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
}
