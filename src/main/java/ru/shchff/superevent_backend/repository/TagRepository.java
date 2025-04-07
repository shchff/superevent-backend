package ru.shchff.superevent_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entity.Tag;
import ru.shchff.superevent_backend.entity.TagType;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>
{
    List<Tag> findByType(TagType type);
}
