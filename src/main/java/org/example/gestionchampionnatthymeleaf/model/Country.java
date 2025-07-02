package org.example.gestionchampionnatthymeleaf.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    private String logo;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Team> teams;
}
