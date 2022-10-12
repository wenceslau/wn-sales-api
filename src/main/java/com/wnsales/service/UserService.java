package com.wnsales.service;

import com.wnsales.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long userId);

    Page<User> findAll(Pageable pageable);

    void delete(Long userId);

    User save(User user);

    User edit(Long userId, User user);

    User partialEdit(Long userId, User user);
}
