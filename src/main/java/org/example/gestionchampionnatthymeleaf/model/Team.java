package org.example.gestionchampionnatthymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"gamesAsTeam1", "gamesAsTeam2", "stadium", "country", "championships"})
@EqualsAndHashCode(exclude = {"gamesAsTeam1", "gamesAsTeam2", "stadium", "country", "championships"})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    private String logo;
    private String coach;
    private String president;
    private String status;
    private String siege;
    private String phone;
    private String website;

    @OneToMany(mappedBy = "team1")
    @JsonIgnore
    private List<Game> gamesAsTeam1;

    @OneToMany(mappedBy = "team2")
    @JsonIgnore
    private List<Game> gamesAsTeam2;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "stadium_id")
    @JsonIgnore
    private Stadium stadium;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    private Set<Championship> championships;


    public Team(String name, LocalDate creationDate, String logo, String coach, String president, String status, String siege, String phone, String website) {
        this.name = name;
        this.creationDate = creationDate;
        this.logo = logo;
        this.coach = coach;
        this.president = president;
        this.status = status;
        this.siege = siege;
        this.phone = phone;
        this.website = website;
    }

    public Team(String name, LocalDate creationDate, String logo, String coach, String status, String president, String siege, String phone, String website, Stadium stadium, Country country) {
        this.name = name;
        this.creationDate = creationDate;
        this.logo = logo;
        this.coach = coach;
        this.status = status;
        this.president = president;
        this.siege = siege;
        this.phone = phone;
        this.website = website;
        this.stadium = stadium;
        this.country = country;
    }

    public Team() {

    }
}
