package com.capgemini.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.user.api.User;

@Component
class UserMapper {


    /** 
     * @param user
     * @return UserDto
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }
<<<<<<< HEAD

    
=======
    
   
>>>>>>> 4c4efae... fix after merge
    /** 
     * @param user
     * @return UserSimpleDto
     */
    UserSimpleDto toSimple(User user) {
<<<<<<< HEAD
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

<<<<<<< HEAD
<<<<<<< HEAD
    UserSimpleDto toSimple(User user) {
<<<<<<< HEAD
        return new UserSimpleDto(user.getId(), 
                           user.getFirstName(), 
                           user.getLastName());
    }

    UserEmailDto toEmail(User user) {
        return new UserEmailDto(user.getId(), 
                            user.getEmail());
=======
=======
>>>>>>> 4c4efae... fix after merge
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

=======
>>>>>>> cd9aa2c... update user
=======
    
    /** 
     * @param user
     * @return UserEmailDto
     */
>>>>>>> a89667f... checkbox
    UserEmailDto toEmail(User user) {
        return new UserEmailDto(user.getId(),
                user.getEmail());
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
    }

    
    /** 
     * @param user
     * @return UserOlderDto
     */
    UserOlderDto toOlder(User user) {
        return new UserOlderDto(user.getId(),
<<<<<<< HEAD
                            user.getFirstName(), 
                            user.getLastName(), 
                            user.getBirthdate());
=======
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate());
>>>>>>> 5e2f8cc... LAB02 in progress (#4)
    }

    
    /** 
     * @param userDto
     * @return User
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    
    /** 
     * @param user
     * @param userDto
     * @return User
     */
    User updateEntity(User user, UserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthdate(userDto.birthdate());
        user.setEmail(userDto.email());
        return user;
    }
}
