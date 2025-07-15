package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Day;

import java.util.List;

public interface DayService {
    void addDay(Day day);

    Day getDayById(Long id);

    List<Day> getAllDaybyChampionshipId(Long championshipId);

}
