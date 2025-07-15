package org.example.gestionchampionnatthymeleaf.service.impl;


import org.example.gestionchampionnatthymeleaf.dto.UserDto;
import org.example.gestionchampionnatthymeleaf.model.User;
import org.example.gestionchampionnatthymeleaf.repository.UserRepository;
import org.example.gestionchampionnatthymeleaf.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

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
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }


    @Override
    public Boolean deleteUser(User user) {
        Long userId = user.getId();
        userRepository.delete(user);
        return userRepository.findById(userId).isEmpty();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public Boolean login(String email, String password) {
//      User user = userRepository.findUserByEmail(email);
//       return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
        return null;

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}
