package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.repository.ChampionshipRepository;
import org.example.gestionchampionnatthymeleaf.service.ChampionshipService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;

    public ChampionshipServiceImpl(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
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
}
