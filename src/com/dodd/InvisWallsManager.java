package com.dodd;

import com.dodd.command.InitWallsCommand;
import net.minecraft.world.level.block.BlockCactus;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class InvisWallsManager {
    List<Location> blueWalls, redWalls;
    TeamManager teamManager;

    BlockData airBlock = Bukkit.createBlockData(Material.AIR);
    BlockData solidBlock = Bukkit.createBlockData(Material.BARRIER);

    public InvisWallsManager(TeamManager teamManager) {
        this.teamManager = teamManager;
        blueWalls = new ArrayList<>();
        redWalls = new ArrayList<>();

        CommandListener.current.registerCommand(new InitWallsCommand(this));
    }

    public void findAllWallBlocks() {
        long startTime = System.nanoTime();

        World world = Bukkit.getWorld(MCTWPlugin.WORLD_NAME);
        int mapSize = 200; //square side width of map
        int minX = -mapSize, minZ = -mapSize;
        int minHeight = 90, maxHeight = 160;

        for (int y = minHeight; y < maxHeight; y++) {
            for (int x = minX; x < mapSize; x ++) {
                for (int z = minZ; z < mapSize; z++) {
                    BlockState block = world.getBlockState(x, y, z);
                    if (block.getBlock().getType() == Material.BLUE_WOOL) {
                        Location location = block.getLocation();
                        System.out.printf("Found blue wool at %s, %s, %s%n", x, y, z);
                        blueWalls.add(location);
                    } else if (block.getBlock().getType() == Material.RED_WOOL) {
                        Location location = block.getLocation();
                        System.out.printf("Found blue wool at %s, %s, %s%n", x, y, z);
                        redWalls.add(location);
                    }
                }
            }
        }

        System.out.println("WALLS FOUND IN " + (System.nanoTime() - startTime) + "nanos");
    }

    public void setWallsForPlayer(Player player, ChatColor color) {
        List<Location> solidWalls = null, airWalls = null;
        if (color == ChatColor.RED) {
            solidWalls = blueWalls;
            airWalls = redWalls;
        } else if (color == ChatColor.BLUE) {
            solidWalls = redWalls;
            airWalls = blueWalls;
        }

        for (Location location : solidWalls) {
            System.out.println("SEtting the block at " + location.toString() + " to solid for player" + player.getDisplayName());
            player.sendBlockChange(location, solidBlock);

        }
        //for (Location location : airWalls)
            //player.sendBlockChange(location, airBlock);
    }

    public void resetWallForPlayer(Player player, Location location, PlayerInteractEvent event) {
        ChatColor color = teamManager.getTeamColorForPlayer(player);

        List<Location> walls = null;
        if (color == ChatColor.BLUE)
            walls = redWalls;
        else if (color == ChatColor.RED)
            walls = blueWalls;
        else
            return;

        for (Location wall : walls) {
            if (wall.getBlockX() == location.getBlockX() && wall.getBlockY() == location.getBlockY() && wall.getBlockZ() == location.getBlockZ()) {
                event.setCancelled(true);
                scheduleBlockReplacement(player, location, solidBlock);
            }
        }
    }

    private void scheduleBlockReplacement(Player player, Location location, BlockData block) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MCTWPlugin.current, new Runnable(){
            @Override
            public void run(){
                player.sendBlockChange(location, block);
            }
        }, 1L);
    }
}
