package org.example.gestionchampionnatthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.repository.ChampionshipRepository;
import org.example.gestionchampionnatthymeleaf.repository.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChampionshipController {

    private final ChampionshipRepository championshipRepository;
    private final TeamRepository teamRepository;

    public ChampionshipController(ChampionshipRepository championshipRepository, TeamRepository teamRepository) {
        this.championshipRepository = championshipRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping("/public/championships")
    public String listChampionships(Model model) {
        List<Championship> championships = championshipRepository.findAll();
        model.addAttribute("championships", championships);
        return "public/championship_list";
    }

    @GetMapping("/private/championship/form")
    public String showChampionshipForm(Model model) {
        model.addAttribute("championship", new Championship());
        model.addAttribute("allTeams", teamRepository.findAll());
        return "private/championship_form";
    }

    @PostMapping("/private/championship/save")
    public String save(@Valid @ModelAttribute Championship championship, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allTeams", teamRepository.findAll());
            return "championship_form";
        }
        championshipRepository.save(championship);
        return "redirect:/public/championships?success";
    }

}
