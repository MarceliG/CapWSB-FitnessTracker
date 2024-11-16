package com.capgemini.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.user.api.User;

@Component
class UserMapper {

    /**
     * Converts a User entity to a UserDto.
     * 
     * @param user The User entity to be converted.
     * @return A UserDto object representing the user with full details.
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Converts a User entity to a simplified UserSimpleDto.
     * 
     * @param user The User entity to be converted.
     * @return A UserSimpleDto object containing basic user information.
     */
    UserSimpleDto toSimple(User user) {
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Converts a User entity to a UserEmailDto.
     * 
     * @param user The User entity to be converted.
     * @return A UserEmailDto object containing only the user's ID and email.
     */
    UserEmailDto toEmail(User user) {
        return new UserEmailDto(user.getId(),
                user.getEmail());
    }

    /**
     * Converts a User entity to a UserOlderDto.
     * 
     * @param user The User entity to be converted.
     * @return A UserOlderDto object containing the user's ID, name, and birthdate.
     */
    UserOlderDto toOlder(User user) {
        return new UserOlderDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate());
    }

    /**
     * Converts a UserDto to a User entity.
     * 
     * @param userDto The UserDto object to be converted.
     * @return A new User entity populated with data from the UserDto.
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    /**
     * Updates an existing User entity with data from a UserDto.
     * 
     * @param user    The existing User entity to be updated.
     * @param userDto The UserDto object containing updated data.
     * @return The updated User entity.
     */
    User updateEntity(User user, UserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthdate(userDto.birthdate());
        user.setEmail(userDto.email());
        return user;
    }
}
