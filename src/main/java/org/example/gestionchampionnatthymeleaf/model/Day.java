package org.example.gestionchampionnatthymeleaf.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le numéro est obligatoire")
    private String number;

    @OneToMany(mappedBy = "day",cascade = CascadeType.ALL)
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

}
