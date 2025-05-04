package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>
{
}
