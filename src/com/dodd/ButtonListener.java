package com.dodd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Attachable;
import org.bukkit.util.Vector;

public class ButtonListener implements Listener {
    TeamManager teamManager;
    InvisWallsManager wallsManager;

    public ButtonListener(TeamManager teamManager, InvisWallsManager wallsManager) {
        this.teamManager = teamManager;
        this.wallsManager = wallsManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null)
            return;

        if (block.getType() == Material.STONE_BUTTON && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Attachable button = (Attachable)block.getState().getData();
            Block attached = block.getRelative(button.getAttachedFace());

            if (attached.getType() == Material.BLUE_CONCRETE) {
                teamManager.addPlayerToTeam(player, ChatColor.BLUE);
            } else if (attached.getType() == Material.RED_CONCRETE) {
                teamManager.addPlayerToTeam(player, ChatColor.RED);
            }
        }

        if (block.getType() == Material.AIR) {
            System.out.println("AIR CLICKED");
            wallsManager.resetWallForPlayer(player, block.getLocation(), event);
        }
    }

    void translateLocationByDirection(Location location, Vector vector) {
        System.out.println(vector.getX() + " " + vector.getY() + " " + vector.getZ());
    }
}
