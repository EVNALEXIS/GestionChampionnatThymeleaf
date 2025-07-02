package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.dto.UserDto;
import org.example.gestionchampionnatthymeleaf.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void  saveUser(UserDto userDto);
    User getUserById(Long id);
    List<UserDto> getAllUsers();
    Boolean deleteUser(User user);
    User updateUser(User user);
    Boolean login(String email, String password);
    User getUserByEmail(String email);
}
