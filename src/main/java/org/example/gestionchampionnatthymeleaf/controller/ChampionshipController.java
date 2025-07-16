package org.example.gestionchampionnatthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.gestionchampionnatthymeleaf.dto.TeamStandingDTO;
import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.service.ChampionshipService;
import org.example.gestionchampionnatthymeleaf.service.DayService;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ChampionshipController {

    private final ChampionshipService championshipService;
    private final TeamService teamService;

    private final DayService dayService;

    public ChampionshipController(ChampionshipService championshipService, TeamService teamService, DayService dayService) {
        this.championshipService = championshipService;
        this.teamService = teamService;

        this.dayService = dayService;
    }

    @GetMapping("public/championships")
    public String listChampionships(Model model) {
        List<Championship> championships = championshipService.getAllChampionships();
        model.addAttribute("championships", championships);
        return "public/championship_list";
    }


    @GetMapping("public/championships/{id}/results")
    public String showResults(@PathVariable Long id, Model model) {
        Championship championship = championshipService.getChampionshipById(id);
        List<Day> days = dayService.getAllDaybyChampionshipId(id);
        List<TeamStandingDTO> standings = championshipService.calculateStandings(id);

        model.addAttribute("championship", championship);
        model.addAttribute("days", days);
        model.addAttribute("standings", standings);

        return "public/championship_results";
    }

    @GetMapping("/private/championships/{id}")
    public String viewChampionshipDetails(@PathVariable Long id, Model model) {
        Championship championship = championshipService.getChampionshipById(id);
        model.addAttribute("championship", championship);
        return "private/championship_details";
    }


    @GetMapping("private/championships/new")
    public String showChampionshipCreateForm(Model model) {
        model.addAttribute("championship", new Championship());
        model.addAttribute("allTeams", teamService.getAllTeams());
        model.addAttribute("pageTitle", "Créer un championnat");
        return "private/championship_form";
    }


    @GetMapping("private/championships/edit/{id}")
    public String showChampionshipEditForm(@PathVariable Long id, Model model) {
        Championship championship = championshipService.getChampionshipById(id);

        if (championship == null) {
            return "redirect:/public/championships?error=notfound";
        }

        model.addAttribute("championship", championship);
        model.addAttribute("allTeams", teamService.getAllTeams());
        model.addAttribute("pageTitle", "Modifier le championnat");
        return "private/championship_form";
    }


    @PostMapping("private/championships/save")
    public String saveChampionship(@Valid @ModelAttribute Championship championship,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allTeams", teamService.getAllTeams());
            model.addAttribute("pageTitle",
                    championship.getId() != null ? "Modifier le championnat" : "Créer un championnat");
            return "private/championship_form";
        }

        try {
            if (championship.getId() != null) {
                // 🔁 Mise à jour
                Championship existingCHampionship = championshipService.getChampionshipById(championship.getId());
                if (existingCHampionship == null) {
                    throw new RuntimeException("Championnat non trouvé avec ID: " + championship.getId());
                }

                // 📝 Mise à jour des champs simples
                existingCHampionship.setName(championship.getName());
                existingCHampionship.setLogo(championship.getLogo());
                existingCHampionship.setLostPoint(championship.getLostPoint());
                existingCHampionship.setWonPoint(championship.getWonPoint());
                existingCHampionship.setDrawPoint(championship.getDrawPoint());
                existingCHampionship.setStartDate(championship.getStartDate());
                existingCHampionship.setEndDate(championship.getEndDate());
                existingCHampionship.setTypeRanking(championship.getTypeRanking());


                // 🔄 Mise à jour des équipes
                if (championship.getTeams() != null) {
                    existingCHampionship.getTeams().clear();
                    existingCHampionship.getTeams().addAll(championship.getTeams());
                }

                championshipService.addChampionship(existingCHampionship);

                redirectAttributes.addFlashAttribute("successMessage",
                        "Championnat '" + existingCHampionship.getName() + "' modifié avec succès !");
            } else {
                // 🆕 Création
                championshipService.addChampionship(championship);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Championnat '" + championship.getName() + "' créé avec succès !");
            }

            return "redirect:/public/championships";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la sauvegarde : " + e.getMessage());
            model.addAttribute("allTeams", teamService.getAllTeams());
            model.addAttribute("pageTitle",
                    championship.getId() != null ? "Modifier le championnat" : "Créer un championnat");
            return "private/championship_form";
        }
    }

    @GetMapping("private/championships/delete/{id}")
    public String deleteTeam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            championshipService.deleteChampionship(id);
            redirectAttributes.addFlashAttribute("successMessage", "Championnat supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/public/championships";
    }

    @GetMapping("private/championships/{championshipId}/days/{dayId}/delete")
    public String deleteDay(@PathVariable Long championshipId, @PathVariable Long dayId) {
        championshipService.removeDayFromChampionship(championshipId, dayId);
        return "redirect:/private/championships/" + championshipId;
    }

}