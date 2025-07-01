package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.model.Game;

import java.util.List;

public interface DayService {
    Day addDay(Day day);
    Day getDayById(Long id);
    List<Day> getAllDays();
    List<Game> getAllGamesByDay(Day day);
}
