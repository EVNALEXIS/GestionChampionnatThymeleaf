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
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/private/championships/create")
    public String showChampionshipCreateForm(Model model) {
        model.addAttribute("championship", new Championship());
        model.addAttribute("allTeams", teamRepository.findAll());
        return "private/championship_form";
    }

    @GetMapping("/private/championships/edit/{id}")
    public String showChampionshipEditForm(@PathVariable Long id, Model model) {
        Championship championship = championshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid championship Id:" + id));
        model.addAttribute("championship", championship);
        model.addAttribute("allTeams", teamRepository.findAll());
        return "private/championship_form";
    }

    @PostMapping("/private/championships/edit/{id}")
    public String updateChampionship(@PathVariable Long id, @Valid @ModelAttribute Championship championship, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allTeams", teamRepository.findAll());
            return "private/championship_form";
        }
        championship.setId(id);
        championshipRepository.save(championship);
        return "redirect:/public/championships?success";
    }

    @PostMapping("/private/championships/save")
    public String save(@Valid @ModelAttribute Championship championship, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allTeams", teamRepository.findAll());
            return "championship_form";
        }
        championshipRepository.save(championship);
        return "redirect:/public/championships?success";
    }

}
