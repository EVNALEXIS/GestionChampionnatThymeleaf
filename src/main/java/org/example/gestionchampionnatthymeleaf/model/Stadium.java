package org.example.gestionchampionnatthymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "teams")
@EqualsAndHashCode(exclude = "teams")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    @NotBlank(message = "L'adresse est obligatoire")
    private String address;
    @Min(value = 1, message = "La capacité doit être supérieure à 0")
    private int capacity;
    private String phone;

    @OneToMany(mappedBy = "stadium")
    @JsonIgnore
    private List<Team> teams;


    public Stadium(String name, String address, int capacity, String phone) {

        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.phone = phone;
    }

    public Stadium() {

    }
}
