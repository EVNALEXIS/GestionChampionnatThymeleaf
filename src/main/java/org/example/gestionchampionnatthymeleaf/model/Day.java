package org.example.gestionchampionnatthymeleaf.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @OneToMany(mappedBy = "day",cascade = CascadeType.ALL)
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

}
