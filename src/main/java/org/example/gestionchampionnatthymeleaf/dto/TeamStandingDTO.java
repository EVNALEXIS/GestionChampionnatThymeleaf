package org.example.gestionchampionnatthymeleaf.dto;

import lombok.Data;
import org.example.gestionchampionnatthymeleaf.model.Team;

@Data
public class TeamStandingDTO {
    private Team team;
    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;

    public TeamStandingDTO(Team team) {
        this.team = team;
    }

    public void update(int goalsFor, int goalsAgainst) {
        this.played++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;

        if (goalsFor > goalsAgainst) wins++;
        else if (goalsFor == goalsAgainst) draws++;
        else losses++;
    }

    public int getPoints() {
        return wins * 3 + draws;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }


}
