package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Game;

public interface GameService {

    void addGame(Game game);

    Game getGameById(Long id);

    void deleteGame(Long id);

}
