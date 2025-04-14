package ru.shchff.superevent_backend.service;

import ru.shchff.superevent_backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    User registerUser(User user);                     // регистрация
    Optional<User> findByEmail(String email);         // найти по email
    Optional<User> findById(Long id);                 // найти по ID
    List<User> getAllUsers();                         // все пользователи
    void blockUser(Long id);                          // блокировка
    void unblockUser(Long id);                        // разблокировка
}