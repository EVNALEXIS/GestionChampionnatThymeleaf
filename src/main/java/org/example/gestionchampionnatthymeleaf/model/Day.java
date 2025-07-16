package org.example.gestionchampionnatthymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"games", "championship"})
@EqualsAndHashCode(exclude = {"games", "championship"})
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le numéro est obligatoire")
    private String number;

    @OneToMany(mappedBy = "day", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonIgnore
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    @JsonIgnore
    private Championship championship;

    public Day(String number, Championship championship) {
        this.number = number;
        this.championship = championship;
    }

    public Day() {
    }

}
