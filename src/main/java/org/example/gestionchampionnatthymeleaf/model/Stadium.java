package org.example.gestionchampionnatthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String adress;
    private int capacity;
    private String phone;

    @OneToMany(mappedBy = "stadium", cascade = CascadeType.ALL)
    private List<Team> teams;
}
