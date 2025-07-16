package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Game;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.repository.GameRepository;
import org.example.gestionchampionnatthymeleaf.repository.TeamRepository;
import org.example.gestionchampionnatthymeleaf.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    public GameServiceImpl(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void addGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with id: " + id));
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.delete(getGameById(id));
    }

    @Override
    public void deleteGameByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));


        gameRepository.deleteAllByTeam1(team);
        gameRepository.deleteAllByTeam2(team);
    }

}
