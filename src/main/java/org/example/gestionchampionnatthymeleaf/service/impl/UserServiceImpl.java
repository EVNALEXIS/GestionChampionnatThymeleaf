package org.example.gestionchampionnatthymeleaf.service.impl;


import org.example.gestionchampionnatthymeleaf.dto.UserDto;
import org.example.gestionchampionnatthymeleaf.model.User;
import org.example.gestionchampionnatthymeleaf.repository.UserRepository;
import org.example.gestionchampionnatthymeleaf.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreationDate(LocalDate.now());
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}
