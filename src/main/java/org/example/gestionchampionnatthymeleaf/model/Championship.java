package org.example.gestionchampionnatthymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    private String logo;

    @NotNull(message = "La date de début est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull(message = "La date de fin est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Min(value = 0, message = "Les points doivent être positifs")
    private int wonPoint;
    @Min(value = 0, message = "Les points doivent être positifs")
    private int lostPoint;
    @Min(value = 0, message = "Les points doivent être positifs")
    private int drawPoint;
    @NotBlank(message = "Le type de classement est obligatoire")
    private String typeRanking;

    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL)
    private List<Day>days;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "championship_team",
            joinColumns = @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams;
}
