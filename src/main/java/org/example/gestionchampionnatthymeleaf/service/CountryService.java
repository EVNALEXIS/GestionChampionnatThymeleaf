package org.example.gestionchampionnatthymeleaf.service;

import org.example.gestionchampionnatthymeleaf.model.Country;

import java.util.List;

public interface CountryService {

    Country addCountry(Country country);

    Country getCountryById(Long id);

    List<Country> getAllCountries();

}
