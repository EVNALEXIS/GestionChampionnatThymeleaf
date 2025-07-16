package org.example.gestionchampionnatthymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"teams", "days"})
@EqualsAndHashCode(exclude = {"teams", "days"})
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

    @OneToMany(mappedBy = "championship", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Day> days;

    @ManyToMany
    @JoinTable(name = "championship_team",
            joinColumns = @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    public Championship(String name, String logo, LocalDate endDate, LocalDate startDate, int wonPoint, int lostPoint, int drawPoint, String typeRanking) {
        this.name = name;
        this.logo = logo;
        this.endDate = endDate;
        this.startDate = startDate;
        this.wonPoint = wonPoint;
        this.lostPoint = lostPoint;
        this.drawPoint = drawPoint;
        this.typeRanking = typeRanking;
    }

    public Championship() {

    }
}
