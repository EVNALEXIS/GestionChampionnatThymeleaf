package org.example.gestionchampionnatthymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    @NotBlank(message = "L'adresse est obligatoire")
    private String adress;
    @NotBlank(message = "La capacité est obligatoire")
    private int capacity;
    private String phone;

    @OneToMany(mappedBy = "stadium", cascade = CascadeType.ALL)
    private List<Team> teams;
}
