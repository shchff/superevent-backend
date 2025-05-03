package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchff.superevent_backend.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
