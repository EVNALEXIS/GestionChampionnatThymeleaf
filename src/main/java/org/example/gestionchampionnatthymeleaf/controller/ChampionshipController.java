package org.example.gestionchampionnatthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.service.ChampionshipService;
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

    public ChampionshipController(ChampionshipService championshipService, TeamService teamService) {
        this.championshipService = championshipService;
        this.teamService = teamService;
    }

    @GetMapping("/public/championships")
    public String listChampionships(Model model) {
        List<Championship> championships = championshipService.getAllChampionships();
        model.addAttribute("championships", championships);
        return "public/championship_list";
    }


    @GetMapping("/private/championships/new")
    public String showChampionshipCreateForm(Model model) {
        model.addAttribute("championship", new Championship());
        model.addAttribute("allTeams", teamService.getAllTeams());
        model.addAttribute("pageTitle", "Créer un championnat");
        return "private/championship_form";
    }


    @GetMapping("/private/championships/edit/{id}")
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


    @PostMapping("/private/championships/save")
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
                // MODIFICATION - l'ID existe
                championshipService.addChampionship(championship); // Votre méthode existante
                redirectAttributes.addFlashAttribute("successMessage",
                        "Championnat '" + championship.getName() + "' modifié avec succès !");
            } else {
                // CRÉATION - pas d'ID
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

}