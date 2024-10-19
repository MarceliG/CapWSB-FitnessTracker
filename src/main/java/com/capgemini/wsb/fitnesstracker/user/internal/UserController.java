package com.capgemini.wsb.fitnesstracker.user.internal;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
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
     * @return List<UserDto>
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * @return List<UserSimpleDto>
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getSimpleAllUser() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimple)
                .toList();
    }

<<<<<<< HEAD
<<<<<<< HEAD
    @GetMapping("/simple")
    public List<UserSimpleDto> getSimpleAllUser() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimple)
                .toList();
    }

<<<<<<< HEAD
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.getUser(id).orElseThrow());
=======
=======
>>>>>>> cd9aa2c... update user
=======
    /**
     * @param userId
     * @return UserDto
     */
>>>>>>> a89667f... checkbox
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userMapper.toDto(userService.getUser(userId).orElseThrow());
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
    }

    /**
     * @param email
     * @return List<UserEmailDto>
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
<<<<<<< HEAD
        List<UserEmailDto> userList = new ArrayList<>();
        UserEmailDto user = userMapper.toEmail(userService.getUserByEmail(email).orElseThrow());

        userList.add(user);
        return userList;
=======
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toEmail)
                .toList();
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
    }

    /**
     * @param time
     * @return List<UserOlderDto>
     */
    @GetMapping("/older/{time}")
    public List<UserOlderDto> getUsersOlderThan(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate time) {
<<<<<<< HEAD
        List<UserOlderDto> users = userService.findAllUsers()
=======
        return userService.findAllUsers()
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
                .stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .map(userMapper::toOlder)
                .toList();
<<<<<<< HEAD

        return users;
=======
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
    }

    /**
     * @param userDto
     * @return User
     * @throws InterruptedException
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        return userService.createUser(userMapper.toEntity(userDto));
    }

    /**
     * @param userId
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * @param userId
     * @param userDto
     * @return User
     */
    @PutMapping("/{userId}")
<<<<<<< HEAD
    public User updateUserEmail(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.getUser(userId).orElseThrow();

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

<<<<<<< HEAD
        // saveUser with Service and return User
        User createdUser = userService.createUser(
                new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email()));

        return createdUser;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUserEmail(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.getUser(userId).orElseThrow();

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

=======
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
        User result = userService.updateUser(user);
        return result;
=======
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User existingUser = userService.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User updatedUser = userMapper.updateEntity(existingUser, userDto);
        return userService.updateUser(updatedUser);
>>>>>>> cd9aa2c... update user
    }

}