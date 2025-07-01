package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Team;

import java.util.List;

public interface TeamService {
    Team addTeam(Team team);
    Team getTeamById(Long id);
    List<Team> getAllTeams();

}
