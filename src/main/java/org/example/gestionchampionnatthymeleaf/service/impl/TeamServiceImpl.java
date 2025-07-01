package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.repository.CountryRepository;
import org.example.gestionchampionnatthymeleaf.repository.TeamRepository;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;


    public TeamServiceImpl(TeamRepository teamRepository, CountryRepository countryRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team addTeam(Team team) {
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


}
