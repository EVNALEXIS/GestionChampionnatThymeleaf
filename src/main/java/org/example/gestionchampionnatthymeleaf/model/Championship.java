package org.example.gestionchampionnatthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo;
    private Date StartDate;
    private Date EndDate;
    private int wonPoint;
    private int lostPoint;
    private int drawPoint;
    private String typeRanking;

    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL)
    private List<Day>days;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "championship_team",
            joinColumns = @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams;
}
