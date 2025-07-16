package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.repository.ChampionshipRepository;
import org.example.gestionchampionnatthymeleaf.repository.DayRepository;
import org.example.gestionchampionnatthymeleaf.service.DayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayServiceImpl implements DayService {

    private final DayRepository dayRepository;
    private final ChampionshipRepository championshipRepository;

    public DayServiceImpl(DayRepository dayRepository, ChampionshipRepository championshipRepository) {
        this.dayRepository = dayRepository;
        this.championshipRepository = championshipRepository;
    }


    @Override
    public void addDay(Day day) {
        dayRepository.save(day);
    }

    @Override
    public Day getDayById(Long id) {
        return dayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + id));
    }

    @Override
    public List<Day> getAllDaybyChampionshipId(Long championshipId) {
        return dayRepository.findByChampionshipId(championshipId);
    }




}
