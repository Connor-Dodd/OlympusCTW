package com.dodd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    public static void playSoundForAll(Sound sound, Location location, float volume, float pitch) {
        Bukkit.getWorld(MCTWPlugin.WORLD_NAME).playSound(location, sound, volume, pitch);
        //for (Player player : Bukkit.getOnlinePlayers())
    }
}
