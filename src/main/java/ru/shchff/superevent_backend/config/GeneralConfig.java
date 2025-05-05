package ru.shchff.superevent_backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shchff.superevent_backend.dto.ModerationRequestDto;
import ru.shchff.superevent_backend.dto.UserUpdateDto;
import ru.shchff.superevent_backend.dto.VenueCreationRequestDto;
import ru.shchff.superevent_backend.dto.VenueDto;
import ru.shchff.superevent_backend.entities.ModerationRequest;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.entities.Venue;

@Configuration
public class GeneralConfig
{
    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(VenueCreationRequestDto.class, Venue.class)
                .addMappings(mapper -> {
                    mapper.skip(Venue::setId);
                    mapper.skip(Venue::setStatus);
                    mapper.skip(Venue::setCreatedAt);
                    mapper.skip(Venue::setTags);
                    mapper.skip(Venue::setImages);
                });

        modelMapper.typeMap(ModerationRequest.class, ModerationRequestDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getVenue().getId(), ModerationRequestDto::setVenueId);
                });

        modelMapper.typeMap(UserUpdateDto.class, User.class)
                .addMappings(m -> {
                    m.skip(User::setId);
                    m.skip(User::setPassword);
                    m.skip(User::setRole);
                    m.skip(User::setStatus);
                });
        modelMapper.typeMap(Venue.class, VenueDto.class)
                .addMappings(m -> {
                    m.skip(VenueDto::setOwnerId);
                    m.skip(VenueDto::setCategoryId);
                    m.skip(VenueDto::setTagIds);
                    m.skip(VenueDto::setImagesPaths);
                });

        return modelMapper;
    }
}
