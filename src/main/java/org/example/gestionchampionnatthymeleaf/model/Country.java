package org.example.gestionchampionnatthymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"teams"})
@EqualsAndHashCode(exclude = {"teams"})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    private String logo;

    @OneToMany(mappedBy = "country")
    private List<Team> teams;

    public Country(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public Country() {
    }
}
