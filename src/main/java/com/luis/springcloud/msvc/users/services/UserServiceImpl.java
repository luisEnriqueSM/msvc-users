package com.luis.springcloud.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luis.springcloud.msvc.users.entities.Role;
import com.luis.springcloud.msvc.users.entities.User;
import com.luis.springcloud.msvc.users.repositories.RoleRepository;
import com.luis.springcloud.msvc.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = this.roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(role -> roles.add(role));
        user.setRoles(roles);
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

}
