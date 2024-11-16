package com.capgemini.wsb.fitnesstracker.user.internal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Retrieves a list of all users.
     * 
     * @return A list of UserDto objects representing all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a simplified list of users.
     * 
     * @return A list of UserSimpleDto objects with basic user information.
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getSimpleAllUser() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimple)
                .toList();
    }

    /**
     * Retrieves user details by their ID.
     * 
     * @param userId The ID of the user.
     * @return A UserDto object representing the user.
     */
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userMapper.toDto(userService.getUser(userId).orElseThrow());
    }

    /**
     * Searches for users by email address.
     * 
     * @param email The email address of the user.
     * @return A list of UserEmailDto objects representing users with the specified
     *         email.
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toEmail)
                .toList();
    }

    /**
     * Retrieves users who are older than a specified date.
     * 
     * @param time Date in the format yyyy-MM-dd.
     * @return A list of UserOlderDto objects representing users born before the
     *         specified date.
     */
    @GetMapping("/older/{time}")
    public List<UserOlderDto> getUsersOlderThan(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate time) {
        return userService.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .map(userMapper::toOlder)
                .toList();
    }

    /**
     * Adds a new user to the system.
     * 
     * @param userDto A UserDto object representing the new user's details.
     * @return A User object representing the added user.
     * @throws InterruptedException If the addition operation is interrupted.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        return userService.createUser(userMapper.toEntity(userDto));
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param userId The ID of the user to delete.
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * Updates an existing user's information.
     * 
     * @param userId  The ID of the user to update.
     * @param userDto A UserDto object containing the updated user information.
     * @return The updated User object.
     */
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User existingUser = userService.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User updatedUser = userMapper.updateEntity(existingUser, userDto);
        return userService.updateUser(updatedUser);
    }

}
