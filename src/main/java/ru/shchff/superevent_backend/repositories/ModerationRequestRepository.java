package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.ModerationRequest;

public interface ModerationRequestRepository extends JpaRepository<ModerationRequest, Long>
{

}
