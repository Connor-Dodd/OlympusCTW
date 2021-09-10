package com.dodd;

import com.dodd.command.HelloWorldCommand;
import com.dodd.gameclass.GameClassManager;
import net.minecraft.server.commands.CommandList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class MCTWPlugin extends JavaPlugin {
    CommandListener commandListener;
    TeamManager teamManager;
    GameClassManager classManager;

    public static MCTWPlugin current;
    public static final String WORLD_NAME = "world";

    public TeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public void onEnable() {
        current = this;

        System.out.println("PLUGIN STARTING");
        this.commandListener = new CommandListener(this);

        this.teamManager = new TeamManager(this);
        this.classManager = new GameClassManager(this);

        BlockLogger blockLogger = new BlockLogger(this);

        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MCTW v1 initialised");
        CommandListener.current.registerCommand(new HelloWorldCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MCTW disabled");
    }
}
