package org.example.gestionchampionnatthymeleaf.controller;

import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TeamController {

    private TeamService teamService;

    @GetMapping("/teams")
    public String getTeams(Model model) {
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "teams";
    }


}
