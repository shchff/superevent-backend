package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{
    List<Appointment> findByVenueId(Long venueId);

    List<Appointment> findByClientIdAndDateAfter(Long clientId, LocalDate now);
}
