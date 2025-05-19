package ru.shchff.superevent_backend.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchff.superevent_backend.dto.RegisterRequestDto;
import ru.shchff.superevent_backend.dto.UserUpdateDto;
import ru.shchff.superevent_backend.entities.Role;
import ru.shchff.superevent_backend.entities.User;
import ru.shchff.superevent_backend.entities.UserStatus;
import ru.shchff.superevent_backend.repositories.UserRepository;
import ru.shchff.superevent_backend.util.UserAlreadyExistsException;
import ru.shchff.superevent_backend.util.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public void registerUser(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.CLIENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findOne(long id)
    {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User updateUser(long id, UserUpdateDto updateDto)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (updateDto.getName() != null) {
            user.setName(updateDto.getName());
        }
        if (updateDto.getSurname() != null) {
            user.setSurname(updateDto.getSurname());
        }

        if (user.getRole() == Role.VENUE_OWNER) {
            if (updateDto.getPhoneNumber() != null) {
                user.setPhoneNumber(updateDto.getPhoneNumber());
            }
            if (updateDto.getRegistrationCertificatePath() != null) {
                user.setRegistrationCertificatePath(updateDto.getRegistrationCertificatePath());
            }
        }

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}