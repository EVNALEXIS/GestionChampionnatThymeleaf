package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Country;
import org.example.gestionchampionnatthymeleaf.model.Team;

import java.util.List;

public interface CountryService {

    Country addCountry(Country country);
    Country getCountryById(Long id);
    List<Country> getAllCountries();
    List<Team>getAllTeamsByCountry(Country country);

}
