package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.dto.UserDto;
import org.example.gestionchampionnatthymeleaf.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User getUserByEmail(String email);
}
