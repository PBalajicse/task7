package com.example.tesing.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tesing.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Handles TC02 (Duplicate email verification)
    boolean existsByEmail(String email);
    
    Optional<User> findByEmail(String email);

}
