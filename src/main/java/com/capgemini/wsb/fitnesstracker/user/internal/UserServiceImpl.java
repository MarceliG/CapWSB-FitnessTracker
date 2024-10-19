package com.capgemini.wsb.fitnesstracker.user.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final Long userId) {
        log.info("Deleting User {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("User not exists in DB, update is not permitted!");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(final User updatedUser) {
        log.info("Updating User {}", updatedUser);
        if (updatedUser.getId() == null) {
            throw new IllegalArgumentException("User not exists in DB, update is not permitted!");
        }

        return userRepository.save(updatedUser);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}