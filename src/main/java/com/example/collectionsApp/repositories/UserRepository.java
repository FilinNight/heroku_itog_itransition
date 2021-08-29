package com.example.collectionsApp.repositories;

import com.example.collectionsApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    Optional<User> findById(Long id);
    void deleteById(Long id);
}
