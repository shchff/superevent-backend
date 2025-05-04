package ru.shchff.superevent_backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shchff.superevent_backend.dto.VenueCreationRequest;
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

        modelMapper.typeMap(VenueCreationRequest.class, Venue.class)
                .addMappings(mapper -> {
                    mapper.skip(Venue::setId);
                    mapper.skip(Venue::setStatus);
                    mapper.skip(Venue::setCreatedAt);
                    mapper.skip(Venue::setTags);
                    mapper.skip(Venue::setImages);
                });
        return modelMapper;
    }
}
