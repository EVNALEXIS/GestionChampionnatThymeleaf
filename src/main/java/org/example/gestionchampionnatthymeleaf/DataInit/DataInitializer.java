package org.example.gestionchampionnatthymeleaf.DataInit;

import org.example.gestionchampionnatthymeleaf.model.*;
import org.example.gestionchampionnatthymeleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {




      // --- Users ---
       if (userRepository.count() == 0) {
          User admin = new User();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@admin.fr");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setCreationDate(LocalDate.now());
            userRepository.save(admin);
        }

        // --- Country ---
        Country france = new Country();
        france.setName("France");
        france.setLogo("france.png");
        countryRepository.save(france);

        // --- Stadiums ---
        Stadium stadeGerland = new Stadium("Stade Gerland", "Lyon", 42000, "0478999999");
        Stadium stadeLouisII = new Stadium("Stade Louis II", "Monaco", 18500, "0493422222");
        Stadium stadeBollaert = new Stadium("Stade Bollaert-Delelis", "Lens", 38000, "0321245678");
        Stadium stadeMatmut = new Stadium("Stade Matmut Atlantique", "Bordeaux", 42000, "0556778899");

        stadiumRepository.saveAll(List.of(stadeGerland, stadeLouisII, stadeBollaert, stadeMatmut));

        // --- Ligue 1 ---
        Championship ligue1 = new Championship();
        ligue1.setName("Ligue 1 Uber Eats");
        ligue1.setLogo("ligue1.png");
        ligue1.setStartDate(LocalDate.of(2024, 8, 9));
        ligue1.setEndDate(LocalDate.of(2025, 5, 25));
        ligue1.setWonPoint(3);
        ligue1.setDrawPoint(1);
        ligue1.setLostPoint(0);
        ligue1.setTypeRanking("DIFFERENCE_BUTS");
        championshipRepository.save(ligue1);

        Set<Team> ligue1Teams = Set.of(
                new Team("Olympique Lyonnais", LocalDate.of(1950, 5, 27), "ol.png", "Laurent Blanc", "Pro", "John Textor", "Lyon", "0478006060", "https://ol.fr", stadeGerland,france),
                new Team("AS Monaco", LocalDate.of(1924, 8, 23), "monaco.png", "Philippe Clement", "Pro", "Dmitry Rybolovlev", "Monaco", "0493421111", "https://asmoneaco.mc",  stadeLouisII,france),
                new Team("RC Lens", LocalDate.of(1906, 6, 17), "lens.png", "Franck Haise", "Pro", "Joseph Oughourlian", "Lens", "0321245000", "https://rclens.fr",  stadeBollaert,france),
                new Team("Girondins de Bordeaux", LocalDate.of(1881, 10, 1), "https://upload.wikimedia.org/wikipedia/fr/7/76/Logo_des_Girondins_de_Bordeaux.svg", "David Guion", "Amateur", "King Street", "Bordeaux", "0556770000", "https://girondins.com",  stadeMatmut,france)
        );

        teamRepository.saveAll(ligue1Teams);
        ligue1.setTeams(ligue1Teams);
        championshipRepository.save(ligue1);

        // --- Days and Games for Ligue 1 ---
        Day day1 = new Day();
        day1.setNumber("1");
        day1.setChampionship(ligue1);
        dayRepository.save(day1);

        Day day2 = new Day();
        day2.setNumber("2");
        day2.setChampionship(ligue1);
        dayRepository.save(day2);

        List<Team> ligue1TeamsList = new ArrayList<>(ligue1Teams);

        Game game1 = new Game(1, 3, ligue1TeamsList.get(0), ligue1TeamsList.get(1), day1);
        Game game2 = new Game(2, 2, ligue1TeamsList.get(2), ligue1TeamsList.get(3), day1);
        Game game3 = new Game(3, 3, ligue1TeamsList.get(3), ligue1TeamsList.get(1), day2);
        Game game4 = new Game(4, 2, ligue1TeamsList.get(2), ligue1TeamsList.get(0), day2);
        gameRepository.saveAll(List.of(game1, game2,game3, game4));




       }
}

