package com.luis.springcloud.msvc.users.repositories;

import org.springframework.data.repository.CrudRepository;

import com.luis.springcloud.msvc.users.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);
}
