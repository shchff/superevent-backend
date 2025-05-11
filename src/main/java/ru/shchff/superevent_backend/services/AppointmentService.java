package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.AppointmentDto;
import ru.shchff.superevent_backend.entities.Appointment;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.entities.Venue;
import ru.shchff.superevent_backend.repositories.AppointmentRepository;
import ru.shchff.superevent_backend.repositories.UserRepository;
import ru.shchff.superevent_backend.repositories.VenueRepository;
import ru.shchff.superevent_backend.util.UserNotFoundException;
import ru.shchff.superevent_backend.util.VenueNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService
{
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
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

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto)
    {
        Appointment appointment = new Appointment();

        appointment.setDate(appointmentDto.getDate());

        Long clientId = appointmentDto.getClientId();
        User user = userRepository.findById(clientId).orElseThrow(() -> new UserNotFoundException(clientId));
        appointment.setClient(user);

        Long venueId = appointmentDto.getVenueId();
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new VenueNotFoundException(venueId));
        appointment.setVenue(venue);

        appointment = appointmentRepository.save(appointment);

        return convertToDto(appointment);
    }

    @Transactional
    public void deleteAppointment(Long id)
    {
        appointmentRepository.deleteById(id);
    }

    private AppointmentDto convertToDto(Appointment appointment)
    {
        AppointmentDto appointmentDto = new AppointmentDto();

        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());

        appointmentDto.setClientId(appointment.getClient().getId());
        appointmentDto.setVenueId(appointment.getVenue().getId());

        return appointmentDto;
    }
}
