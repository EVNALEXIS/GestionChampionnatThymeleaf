package org.example.gestionchampionnatthymeleaf.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String logo;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Team> teams;
}
