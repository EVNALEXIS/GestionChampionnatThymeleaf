package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.dto.TeamStandingDTO;
import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.model.Game;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.repository.ChampionshipRepository;
import org.example.gestionchampionnatthymeleaf.service.ChampionshipService;
import org.example.gestionchampionnatthymeleaf.service.DayService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final DayService dayService;

    public ChampionshipServiceImpl(ChampionshipRepository championshipRepository, DayService dayService) {
        this.championshipRepository = championshipRepository;
        this.dayService = dayService;
    }


    @Override
    public Championship addChampionship(Championship championship) {
        return championshipRepository.save(championship);
    }

    @Override
    public Championship getChampionshipById(Long id) {
        return championshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Championship not found with id: " + id));
    }

    @Override
    public List<Championship> getAllChampionships() {
        return championshipRepository.findAll();
    }

    @Override
    public void deleteChampionship(Long id) {
        Championship champ = championshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        // Détacher les équipes liées (si ManyToMany avec équipes)
        for (Team team : champ.getTeams()) {
            team.getChampionships().remove(champ);
        }
        champ.getTeams().clear();

        // Puis supprimer le championnat
        championshipRepository.delete(champ);
    }

    @Override
    public List<TeamStandingDTO> calculateStandings(Long championshipId) {
        List<Day> days = dayService.getAllDaybyChampionshipId(championshipId);
        Map<Long, TeamStandingDTO> standingsMap = new HashMap<>();

        for (Day day : days) {
            for (Game game : day.getGames()) {
                if (game.getTeam1() == null || game.getTeam2() == null) continue;

                // Ignorer les matchs non joués (par exemple score par défaut = 0 ou null si modifié)
                if (game.getTeam1points() < 0 || game.getTeam2points() < 0) continue;

                // Équipe 1
                standingsMap.putIfAbsent(game.getTeam1().getId(), new TeamStandingDTO(game.getTeam1()));
                standingsMap.get(game.getTeam1().getId()).update(game.getTeam1points(), game.getTeam2points());

                // Équipe 2
                standingsMap.putIfAbsent(game.getTeam2().getId(), new TeamStandingDTO(game.getTeam2()));
                standingsMap.get(game.getTeam2().getId()).update(game.getTeam2points(), game.getTeam1points());
            }
        }

        List<TeamStandingDTO> standings = new ArrayList<>(standingsMap.values());

        standings.sort(Comparator
                .comparing(TeamStandingDTO::getPoints).reversed()
                .thenComparing(TeamStandingDTO::getGoalDifference, Comparator.reverseOrder())
                .thenComparing(TeamStandingDTO::getGoalsFor, Comparator.reverseOrder())
                .thenComparing(dto -> dto.getTeam().getName()));

        return standings;
    }

}
