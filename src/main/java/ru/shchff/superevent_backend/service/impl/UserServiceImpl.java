package ru.shchff.superevent_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shchff.superevent_backend.entity.User;
import ru.shchff.superevent_backend.entity.UserStatus;
import ru.shchff.superevent_backend.repository.UserRepository;
import ru.shchff.superevent_backend.service.UserService;

import java.util.Optional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // для шифровки пароля

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE); // по умолчанию активен
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public void blockUser(Long id)
    {
        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(UserStatus.BLOCKED);
            userRepository.save(user);
        });
    }

    @Override
    public void unblockUser(Long id)
    {
        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        });
    }
}
