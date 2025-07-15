package org.example.gestionchampionnatthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.model.Country;
import org.example.gestionchampionnatthymeleaf.model.Stadium;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.service.ChampionshipService;
import org.example.gestionchampionnatthymeleaf.service.CountryService;
import org.example.gestionchampionnatthymeleaf.service.StadiumService;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/private/teams")

@Controller
public class TeamController {

    private final TeamService teamService;
    private final ChampionshipService championshipService;
    private final CountryService countryService;
    private final StadiumService stadiumService;

    public TeamController(TeamService teamService, ChampionshipService championshipService,
                          CountryService countryService, StadiumService stadiumService) {
        this.teamService = teamService;
        this.championshipService = championshipService;
        this.countryService = countryService;
        this.stadiumService = stadiumService;
    }

    @GetMapping
    public String listTeams(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "private/teams_list";
    }

    @GetMapping("/new")
    public String showTeamCreateForm(Model model) {
        Team team = new Team();
        team.setStadium(new Stadium());
        team.setCountry(new Country());

        populateModel(model, team);
        return "private/team_form";
    }

    @GetMapping("/edit/{id}")
    public String showTeamEditForm(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id);
        if (team == null) {
            return "redirect:/private/teams?error=notfound";
        }

        if (team.getStadium() == null) team.setStadium(new Stadium());
        if (team.getCountry() == null) team.setCountry(new Country());

        populateModel(model, team);
        return "private/team_form"; // <-- corriger ici aussi
    }

    @PostMapping("/save")
    public String saveTeam(@Valid @ModelAttribute Team team,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes,
                           @RequestParam(value = "stadiumChoice", required = false) String stadiumChoice,
                           @RequestParam(value = "existingStadiumId", required = false) Long existingStadiumId,
                           @RequestParam(value = "countryChoice", required = false) String countryChoice,
                           @RequestParam(value = "existingCountryId", required = false) Long existingCountryId
    ) {
        System.out.println(">>> SUBMIT DÉTECTÉ !");
        System.out.println(">>> Team name: " + team.getName());
        System.out.println(">>> Stadium choice: " + stadiumChoice + ", Existing stadium ID: " + existingStadiumId);
        System.out.println(">>> Country choice: " + countryChoice + ", Existing country ID: " + existingCountryId);

        // Pré-remplissage si null
        if (stadiumChoice == null) {
            stadiumChoice = (team.getStadium() != null && team.getStadium().getId() != null) ? "existing" : "new";
        }
        if (countryChoice == null) {
            countryChoice = (team.getCountry() != null && team.getCountry().getId() != null) ? "existing" : "new";
        }

        // === VALIDATIONS ===
        if ("existing".equals(stadiumChoice) && existingStadiumId == null) {
            System.out.println(">>> Erreur : aucun stade existant sélectionné");
            bindingResult.rejectValue("stadium", "error.team.stadium.required", "Veuillez sélectionner un stade existant.");
        } else if ("new".equals(stadiumChoice)) {
            if (team.getStadium().getName() == null || team.getStadium().getName().trim().isEmpty()) {
                System.out.println(">>> Erreur : nom du stade manquant");
                bindingResult.rejectValue("stadium.name", "error.team.stadium.name.required", "Le nom du stade est obligatoire.");
            }
            if (team.getStadium().getAddress() == null || team.getStadium().getAddress().trim().isEmpty()) {
                System.out.println(">>> Erreur : adresse du stade manquante");
                bindingResult.rejectValue("stadium.address", "error.team.stadium.address.required", "L'adresse du stade est obligatoire.");
            }
            if (team.getStadium().getCapacity() <= 0) {
                System.out.println(">>> Erreur : capacité du stade invalide");
                bindingResult.rejectValue("stadium.capacity", "error.team.stadium.capacity.required", "La capacité du stade doit être supérieure à 0.");
            }
        }

        if ("existing".equals(countryChoice) && existingCountryId == null) {
            System.out.println(">>> Erreur : aucun pays existant sélectionné");
            bindingResult.rejectValue("country", "error.team.country.required", "Veuillez sélectionner un pays existant.");
        } else if ("new".equals(countryChoice)) {
            if (team.getCountry().getName() == null || team.getCountry().getName().trim().isEmpty()) {
                System.out.println(">>> Erreur : nom du pays manquant");
                bindingResult.rejectValue("country.name", "error.team.country.name.required", "Le nom du pays est obligatoire.");
            }
        }

        if (bindingResult.hasErrors()) {
            System.out.println(">>> Des erreurs de validation ont été trouvées :");
            bindingResult.getAllErrors().forEach(err ->
                    System.out.println(" - " + err.getDefaultMessage())
            );
            populateModel(model, team);
            return "private/team_form";
        }

        try {
            // === Gestion du stade ===
            if ("existing".equals(stadiumChoice)) {
                Stadium stadium = stadiumService.getStadiumById(existingStadiumId);
                System.out.println(">>> Stade existant sélectionné : " + stadium);
                team.setStadium(stadium);
            } else {
                Stadium newStadium = team.getStadium();
                newStadium.setId(null);
                Stadium savedStadium = stadiumService.addStadium(newStadium);
                System.out.println(">>> Nouveau stade sauvegardé : " + savedStadium.getName());
                team.setStadium(savedStadium);
            }

            // === Gestion du pays ===
            if ("existing".equals(countryChoice)) {
                Country country = countryService.getCountryById(existingCountryId);
                System.out.println(">>> Pays existant sélectionné : " + country);
                team.setCountry(country);
            } else {
                Country newCountry = team.getCountry();
                newCountry.setId(null);
                Country savedCountry = countryService.addCountry(newCountry);
                System.out.println(">>> Nouveau pays sauvegardé : " + savedCountry.getName());
                team.setCountry(savedCountry);
            }

            // === Gestion des championnats ===
            List<Championship> selected = new ArrayList<>();
            if (team.getChampionships() != null) {
                for (Championship champ : team.getChampionships()) {
                    if (champ.getId() != null) {
                        Championship fullChamp = championshipService.getChampionshipById(champ.getId());
                        if (fullChamp != null) selected.add(fullChamp);
                    }
                }
            }
            team.setChampionships(selected);
            System.out.println(">>> Championnats associés : " + selected.size());

            // === Sauvegarde ===
            Team saved = teamService.addTeam(team);
            System.out.println(">>> Équipe sauvegardée avec ID : " + saved.getId());

            redirectAttributes.addFlashAttribute("successMessage", (team.getId() != null)
                    ? "Équipe modifiée avec succès !" : "Équipe créée avec succès !");
            return "redirect:/private/teams";

        } catch (Exception e) {
            System.out.println(">>> ERREUR lors de la sauvegarde : " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Erreur lors de la sauvegarde : " + e.getMessage());
            populateModel(model, team);
            return "private/team_form";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Team team = teamService.getTeamById(id);
            if (team == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Équipe introuvable.");
            } else {
                teamService.deleteTeam(id);
                redirectAttributes.addFlashAttribute("successMessage", "Équipe supprimée avec succès !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/private/teams";
    }

    @GetMapping("/{id}")
    public String viewTeam(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id);
        if (team == null) {
            return "redirect:/private/teams?error=notfound";
        }
        model.addAttribute("team", team);
        return "private/team_view";
    }

    private void populateModel(Model model, Team team) {
        model.addAttribute("team", team);
        model.addAttribute("allChampionships", championshipService.getAllChampionships());
        model.addAttribute("allStadiums", stadiumService.getAllStadiums());
        model.addAttribute("allCountries", countryService.getAllCountries());
        model.addAttribute("pageTitle", (team.getId() != null) ? "Modifier l'équipe" : "Créer une équipe");
    }
}
