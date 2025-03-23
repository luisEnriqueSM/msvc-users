package com.luis.springcloud.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setRoles(getRoles(user));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> update(User user, Long id) {
        Optional<User> userOptional = this.findById(id);

        return userOptional.map(userDB -> {
            userDB.setEmail(user.getEmail());
            userDB.setUsername(user.getUsername());
            Optional.ofNullable(user.isEnabled()).ifPresent(userDB::setEnabled);
            userDB.setRoles(getRoles(user));
            return Optional.of(this.userRepository.save(userDB));
        }).orElseGet(() -> Optional.empty()); 
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

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = this.roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(roles::add);

        if(user.isAdmin()){
            Optional<Role> adminOptional = this.roleRepository.findByName("ROLE_ADMIN");
            adminOptional.ifPresent(roles::add);
        }
        return roles;
    }

}
