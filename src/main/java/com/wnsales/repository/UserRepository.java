package com.wnsales.repository;

import com.wnsales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long id);

}