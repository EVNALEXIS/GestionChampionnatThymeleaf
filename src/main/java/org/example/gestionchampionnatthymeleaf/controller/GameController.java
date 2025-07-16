package org.example.gestionchampionnatthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.gestionchampionnatthymeleaf.model.Day;
import org.example.gestionchampionnatthymeleaf.model.Game;
import org.example.gestionchampionnatthymeleaf.model.Team;
import org.example.gestionchampionnatthymeleaf.service.DayService;
import org.example.gestionchampionnatthymeleaf.service.GameService;
import org.example.gestionchampionnatthymeleaf.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Set;

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
    @GetMapping("/games/edit/{id}")
    public String showGameEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Game game = gameService.getGameById(id);
        if (game == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Match introuvable.");
            return "redirect:/private/championships";
        }

        Day day = game.getDay();
        if (day == null || day.getChampionship() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Jour ou championnat associé introuvable.");
            return "redirect:/private/championships";
        }

        Set<Team> teams = day.getChampionship().getTeams();

        model.addAttribute("game", game);
        model.addAttribute("teams", teams);

        return "private/game_form";
    }

    @PostMapping("/days/{dayId}/games/save")
    public String saveOrUpdateGame(@PathVariable Long dayId,
                                   @RequestParam Long team1Id,
                                   @RequestParam Long team2Id,
                                   @Valid @ModelAttribute("game") Game game,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        Day day = dayService.getDayById(dayId);
        if (day == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Jour non trouvé");
            return "redirect:/private/championships";
        }

        Team team1 = teamService.getTeamById(team1Id);
        Team team2 = teamService.getTeamById(team2Id);

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

        if (game.getId() != null) {
            // Cas édition : on récupère l'existant et on le met à jour
            Game existingGame = gameService.getGameById(game.getId());
            if (existingGame != null) {
                existingGame.setTeam1(team1);
                existingGame.setTeam2(team2);
                existingGame.setTeam1points(game.getTeam1points());
                existingGame.setTeam2points(game.getTeam2points());
                existingGame.setDay(day);
                gameService.addGame(existingGame);
                redirectAttributes.addFlashAttribute("successMessage", "Match mis à jour !");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Match à éditer introuvable");
            }
        } else {
            // Cas ajout
            gameService.addGame(game);
            redirectAttributes.addFlashAttribute("successMessage", "Match créé avec succès !");
        }

        return "redirect:/private/championships/" + day.getChampionship().getId();
    }

    @GetMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Game game = null;
        try {
            game = gameService.getGameById(id);
            if (game == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Match introuvable.");
            } else {
                gameService.deleteGame(id);
                redirectAttributes.addFlashAttribute("successMessage", "Match supprimé avec succès !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }

        // Redirige vers le championnat associé au match (si possible)
        Long championshipId = (game != null && game.getDay() != null && game.getDay().getChampionship() != null)
                ? game.getDay().getChampionship().getId()
                : null;

        if (championshipId != null) {
            return "redirect:/private/championships/" + championshipId;
        } else {
            return "redirect:/private/championships";
        }
    }


}
