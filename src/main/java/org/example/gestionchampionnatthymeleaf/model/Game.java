package org.example.gestionchampionnatthymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"team1", "team2", "day"})
@EqualsAndHashCode(exclude = {"team1", "team2", "day"})
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int team1points;
    private int team2points;


    @ManyToOne
    @JoinColumn(name = "team1_id")
    @JsonIgnoreProperties({"gamesAsTeam1", "gamesAsTeam2"})
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    @JsonIgnoreProperties({"gamesAsTeam1", "gamesAsTeam2"})
    private Team team2;

    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnoreProperties("games")
    private Day day;

    public Game(int team1points, int team2points, Team team1, Team team2, Day day) {
        this.team1points = team1points;
        this.team2points = team2points;
        this.team1 = team1;
        this.team2 = team2;
        this.day = day;
    }
}
