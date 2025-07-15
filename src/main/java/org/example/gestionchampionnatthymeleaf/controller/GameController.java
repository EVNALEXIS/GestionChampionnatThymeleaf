package org.example.gestionchampionnatthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.model.Game;
import org.example.gestionchampionnatthymeleaf.service.DayService;
import org.example.gestionchampionnatthymeleaf.service.GameService;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/private")
public class GameController {

    private final GameService gameService;
    private final DayService dayService;
    private final TeamService teamService;

    public GameController(GameService gameService, DayService dayService, TeamService teamService) {
        this.gameService = gameService;
        this.dayService = dayService;
        this.teamService = teamService;
    }

    @GetMapping("/days/{dayId}/games/new")
    public String showCreateGameForm(@PathVariable Long dayId, Model model) {
        Day day = dayService.getDayById(dayId);
        Game game = new Game();
        game.setDay(day);
        model.addAttribute("game", game);
        model.addAttribute("teams", day.getChampionship().getTeams());
        return "private/game_form";
    }

    @PostMapping("/days/{dayId}/games")
    public String createGame(@PathVariable Long dayId,
                             @RequestParam Long team1Id,
                             @RequestParam Long team2Id,
                             @Valid @ModelAttribute Game game,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        Day day = dayService.getDayById(dayId);
        if (day == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Jour non trouvé");
            return "redirect:/private/championships";
        }

        var team1 = teamService.getTeamById(team1Id);
        var team2 = teamService.getTeamById(team2Id);

        if (team1 == null || team2 == null) {
            bindingResult.reject("teams.notFound", "Une ou plusieurs équipes non trouvées");
        }

        if (team1Id.equals(team2Id)) {
            bindingResult.reject("teams.same", "Une équipe ne peut pas jouer contre elle-même");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", day.getChampionship().getTeams());
            model.addAttribute("game", game);
            return "private/game_form";
        }

        game.setTeam1(team1);
        game.setTeam2(team2);
        game.setDay(day);

        gameService.addGame(game);
        return "redirect:/private/championships/" + day.getChampionship().getId();
    }


}
