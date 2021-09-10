package com.dodd.gameclass;

import com.dodd.CommandListener;
import com.dodd.MCTWPlugin;
import com.dodd.command.InitWallsCommand;
import com.dodd.command.SetClassCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class GameClassManager {
    private GameClassListener listener;
    private HashMap<String, BaseGameClass> playerClasses;
    private BukkitRunnable playerUpdateLoop;

    public enum GameClasses {Cleric, Rogue};

    public GameClassManager(MCTWPlugin plugin) {
        playerClasses = new HashMap<>();
        listener = new GameClassListener(this);

        Bukkit.getPluginManager().registerEvents(listener, plugin);
        CommandListener.current.registerCommand(new SetClassCommand(this));

        startPlayerUpdateLoop(plugin);
    }

    public void startPlayerUpdateLoop(MCTWPlugin plugin) {
        playerUpdateLoop = new BukkitRunnable() {
            @Override
            public void run() {
                for (BaseGameClass player : playerClasses.values()) {
                    player.addUltimateProgress(10);
                }
            }
        };
        playerUpdateLoop.runTaskTimer(plugin, 10L, 10L);
    }

    public void assignPlayerClass(Player player, GameClasses className) {
        if (playerClasses.containsKey(player.getName()))
            playerClasses.remove(player.getName());

        BaseGameClass toAssign = null;
        switch (className) {
            case Rogue:
                toAssign = new Rogue(player);
                break;
            default:
                break;

        }
        playerClasses.put(player.getName(), toAssign);
    }

    public BaseGameClass getClassForPlayer(Player player) {
        return playerClasses.get(player.getName());
    }

    public void castPlayerSwordAbility(Player player) {

    }

    public void castPlayerProjectileAbility(Player player) {

    }

    public void castPlayerUltimate(Player player) {

    }
}
