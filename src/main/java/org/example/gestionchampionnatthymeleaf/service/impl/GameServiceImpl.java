package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Game;
import org.example.gestionchampionnatthymeleaf.repository.GameRepository;
import org.example.gestionchampionnatthymeleaf.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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

}
