package com.capgemini.wsb.fitnesstracker.user.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final TrainingServiceImpl trainingServiceImpl;

    /**
     * Creates a new user in the database.
     *
     * @param user The User entity to be created.
     * @return The created User entity with a database ID.
     * @throws IllegalArgumentException if the user already has a database ID.
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param updatedUser The User entity with updated information.
     * @return The updated User entity.
     * @throws IllegalArgumentException if the user does not have an existing database ID.
     */
    @Override
    public User updateUser(final User updatedUser) {
        log.info("Updating User {}", updatedUser);
        if (updatedUser.getId() == null) {
            throw new IllegalArgumentException("User not exists in DB, update is not permitted!");
        }

        return userRepository.save(updatedUser);
    }

    /**
     * Deletes a user and their associated training data from the database.
     *
     * @param userId The ID of the user to delete.
     * @throws IllegalArgumentException if the user ID is null.
     */
    @Override
    public void deleteUser(final Long userId) {
        log.info("Deleting User {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("User not exists in DB, update is not permitted!");
        }

        trainingServiceImpl.deleteTrainingByUserId(userId);
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return An Optional containing the User entity if found, or empty if not found.
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return An Optional containing the User entity if found, or empty if not found.
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves all users in the database.
     *
     * @return A list of all User entities.
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}
