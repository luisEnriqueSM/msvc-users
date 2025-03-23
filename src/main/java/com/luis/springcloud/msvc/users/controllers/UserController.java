package com.luis.springcloud.msvc.users.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luis.springcloud.msvc.users.entities.User;
import com.luis.springcloud.msvc.users.services.UserService;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = null;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id){
        Optional<User> userOptional = this.userService.findById(id);

        return userOptional.map(userDB -> {
            userDB.setEmail(user.getEmail());
            userDB.setUsername(user.getUsername());
            if(user.isEnabled() != null){
                userDB.setEnabled(user.isEnabled());
            }
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.save(userDB));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = this.userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username){
        Optional<User> user = this.userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> list(){
        return ResponseEntity.ok().body(this.userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<User> user = this.userService.findById(id);
        if(user.isPresent()){
            this.userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
