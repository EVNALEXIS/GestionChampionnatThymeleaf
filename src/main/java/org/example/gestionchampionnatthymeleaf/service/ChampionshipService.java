package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.dto.TeamStandingDTO;
import org.example.gestionchampionnatthymeleaf.model.Championship;

import java.util.List;

public interface ChampionshipService {

    Championship addChampionship(Championship championship);

    Championship getChampionshipById(Long id);

    List<Championship> getAllChampionships();

    void deleteChampionship(Long id);

    List<TeamStandingDTO> calculateStandings(Long championshipId);


}
