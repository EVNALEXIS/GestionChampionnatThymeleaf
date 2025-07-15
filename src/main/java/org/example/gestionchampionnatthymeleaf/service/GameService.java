package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Game;

import java.util.List;

public interface GameService {

    Game addGame(Game game);

    Game getGameById(Long id);

    List<Game> getAllGames();
}
