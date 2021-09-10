package com.dodd;

import com.dodd.command.InitWallsCommand;
import com.dodd.command.LogBlockClicksCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Attachable;

public class BlockLogger implements Listener {

    public boolean isLogging = false;

    public BlockLogger(MCTWPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        CommandListener.current.registerCommand(new LogBlockClicksCommand(this));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (isLogging && block != null) {
            System.out.println(block.getType().toString());
        }
    }
}
