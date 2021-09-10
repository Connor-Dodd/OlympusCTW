package com.dodd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TeamManager {
    MCTWPlugin plugin;
    HashMap<ChatColor, Team> teams;
    InvisWallsManager wallsManager;
    ButtonListener buttonListener;

    private static final String RED_TEAM_NAME = "Not Quite Pink";
    private static final String BLUE_TEAM_NAME = "Pretty Sky";

    public TeamManager(MCTWPlugin plugin) {
        this.plugin = plugin;
        this.teams = new HashMap<>();
        this.wallsManager = new InvisWallsManager(this);

        this.buttonListener = new ButtonListener(this, wallsManager);
        Bukkit.getPluginManager().registerEvents(buttonListener, plugin);

        ScoreboardManager sm = this.plugin.getServer().getScoreboardManager();
        Scoreboard board = sm.getMainScoreboard();

        Team redTeam = board.getTeam(RED_TEAM_NAME);
        Team blueTeam = board.getTeam(BLUE_TEAM_NAME);

        if (redTeam == null)
            redTeam = board.registerNewTeam(RED_TEAM_NAME);
        redTeam.setColor(ChatColor.RED);
        this.teams.put(ChatColor.RED, redTeam);

        if (blueTeam == null) {
            blueTeam = board.registerNewTeam(BLUE_TEAM_NAME);
        }
        blueTeam.setColor(ChatColor.BLUE);
        this.teams.put(ChatColor.BLUE, blueTeam);
    }

    public void addPlayerToTeam(Player player, ChatColor color) {
        Team team = teams.get(color);
        team.addEntry(player.getDisplayName());
        player.setGlowing(true);

        wallsManager.setWallsForPlayer(player, color);

        player.sendMessage(color + "You have joined the team " + team.getName());
    }

    public ChatColor getTeamColorForPlayer(Player player) {
        for (Team team : teams.values()) {
            for (String entry : team.getEntries()) {
                if (player.getName().equals(entry)) {
                    return team.getColor();
                }
            }
        }

        return null;
    }

    public Collection<String> getTeamAllies(Player player) {
        for (Team team : teams.values()) {
            for (String entry : team.getEntries()) {
                if (player.getName().equals(entry))
                    return team.getEntries();
            }
        }
        return new ArrayList<String>();
    }

    public Collection<String> getTeamEnemies(Player player) {
        teamLoop:
        for (Team team : teams.values()) {
            for (String entry : team.getEntries()) {
                if (player.getName().equals(entry))
                    continue teamLoop;
            }

            return team.getEntries();
        }
        return new ArrayList<String>();
    }
}
