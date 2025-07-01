package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Country;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.repository.CountryRepository;
import org.example.gestionchampionnatthymeleaf.repository.TeamRepository;
import org.example.gestionchampionnatthymeleaf.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final TeamRepository teamRepository;

    public CountryServiceImpl(CountryRepository countryRepository, TeamRepository teamRepository) {
        this.countryRepository = countryRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<Team> getAllTeamsByCountry(Country country) {
        return teamRepository.findByCountry(country);
    }
}
