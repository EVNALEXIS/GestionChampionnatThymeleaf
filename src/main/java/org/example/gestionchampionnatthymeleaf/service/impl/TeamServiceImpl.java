package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.repository.GameRepository;
import org.example.gestionchampionnatthymeleaf.repository.TeamRepository;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;


    public TeamServiceImpl(TeamRepository teamRepository, GameRepository gameRepository) {
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Team addTeam(Team team) {
        if (team.getChampionships() != null) {
            for (Championship champ : team.getChampionships()) {
                champ.getTeams().add(team);
            }
        }
        return teamRepository.save(team);
    }


    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // Dissocier des championnats
        for (Championship champ : team.getChampionships()) {
            champ.getTeams().remove(team);
        }
        team.getChampionships().clear();

        // Supprimer les games où cette équipe est présente (team1 ou team2)
        gameRepository.deleteAllByTeam1(team);
        gameRepository.deleteAllByTeam2(team);

        // Supprimer l'équipe
        teamRepository.delete(team);
    }


}
