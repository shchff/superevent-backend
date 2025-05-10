package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.AppointmentDto;
import ru.shchff.superevent_backend.entities.Appointment;
import ru.shchff.superevent_backend.repositories.AppointmentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService
{
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentsByVenueFromToday(Long venueId)
    {
        List<Appointment> appointments = appointmentRepository
                .findByVenueIdAndDateAfterOrderByDateAsc(venueId, LocalDate.now().minusDays(1));
        return appointments.stream()
                .map(a -> modelMapper.map(a, AppointmentDto.class))
                .collect(Collectors.toList());
    }
}
