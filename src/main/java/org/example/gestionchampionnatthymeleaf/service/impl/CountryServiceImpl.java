package org.example.gestionchampionnatthymeleaf.service.impl;

import org.example.gestionchampionnatthymeleaf.model.Country;
import org.example.gestionchampionnatthymeleaf.repository.CountryRepository;
import org.example.gestionchampionnatthymeleaf.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
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

}
