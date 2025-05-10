package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shchff.superevent_backend.entities.Tag;
import ru.shchff.superevent_backend.repositories.TagRepository;
import ru.shchff.superevent_backend.util.TagNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService
{
    private final TagRepository tagRepository;

    public List<Tag> getAllTags()
    {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long id)
    {
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }

}
