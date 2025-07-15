package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Stadium;

import java.util.List;

public interface StadiumService {
    Stadium addStadium(Stadium stadium);

    Stadium getStadiumById(Long id);

    List<Stadium> getAllStadiums();
}
