package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{
    List<Appointment> findByVenueIdAndDateAfterOrderByDateAsc(Long venueId, LocalDate date);
}
