package org.example.gestionchampionnatthymeleaf.repository;

import org.example.gestionchampionnatthymeleaf.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
