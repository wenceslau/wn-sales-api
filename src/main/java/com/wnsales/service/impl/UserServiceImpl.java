package com.wnsales.service.impl;

import com.wnsales.model.User;
import com.wnsales.repository.UserRepository;
import com.wnsales.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //This annotation already inject constructor with autowired
public class UserServiceImpl extends _DefaultService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new EmptyResultDataAccessException("Resource not found", 1);
        }

        return optionalUser;
    }

    @Override
    public void delete(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        }
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User edit(Long userId, User user) {
        User target = findById(userId).get();

        BeanUtils.copyProperties(user, target, "id");

        return userRepository.save(target);
    }

    @Override
    public User partialEdit(Long userId, User user) {

        User target = findById(userId).get();

        var ignore = new ArrayList<>(List.of("uid"));
        addPropertyNullToIgnore(user, ignore);

        BeanUtils.copyProperties(user, target, ignore.toArray(String[]::new));

        return userRepository.save(target);
    }

}
