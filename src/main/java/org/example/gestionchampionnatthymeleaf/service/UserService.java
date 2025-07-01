package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();

}
