package com.luis.springcloud.msvc.users.services;

import java.util.Optional;

import com.luis.springcloud.msvc.users.entities.User;

public interface UserService {

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Iterable<User> findAll();
    User save(User user);
    void delete(Long id);
}
