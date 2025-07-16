package org.example.gestionchampionnatthymeleaf.controller;

import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.service.ChampionshipService;
import org.example.gestionchampionnatthymeleaf.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/private")
public class DayController {

    private final DayService dayService;
    private final ChampionshipService championshipService;

    public DayController(DayService dayService, ChampionshipService championshipService) {
        this.dayService = dayService;
        this.championshipService = championshipService;
    }

    @GetMapping("/championships/{championshipId}/days/new")
    public String showCreateDayForm(@PathVariable Long championshipId, Model model) {
        Championship championship = championshipService.getChampionshipById(championshipId);
        Day day = new Day();
        day.setChampionship(championship);
        model.addAttribute("day", day);
        model.addAttribute("teams", championship.getTeams());
        return "private/day_form";
    }

    @PostMapping("/championships/{championshipId}/days")
    public String createDay(@PathVariable Long championshipId, @ModelAttribute Day day) {

        Championship championship = championshipService.getChampionshipById(championshipId);
        day.setChampionship(championship);
        dayService.addDay(day);
        return "redirect:/private/championships/" + championshipId;

    }



}
