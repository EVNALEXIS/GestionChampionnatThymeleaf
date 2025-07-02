package org.example.gestionchampionnatthymeleaf.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate creationDate;
}