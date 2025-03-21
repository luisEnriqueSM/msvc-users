package com.luis.springcloud.msvc.users.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luis.springcloud.msvc.users.entities.User;
import com.luis.springcloud.msvc.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {
        return this.repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return this.repository.findAll();
    }

}
