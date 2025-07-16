package org.example.gestionchampionnatthymeleaf.repository;

import org.example.gestionchampionnatthymeleaf.model.Game;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    void deleteAllByTeam1(Team team);

    void deleteAllByTeam2(Team team);

}
