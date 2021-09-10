package com.dodd.gameclass;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;

public class GameClassListener implements Listener {
    GameClassManager manager;

    private static final long ULTIMATE_INTERVAL = 500000000;

    public GameClassListener(GameClassManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        BaseGameClass playerClass = manager.getClassForPlayer(player);

        if (event.getAction() == Action.RIGHT_CLICK_AIR && event.getItem() != null && event.getItem().getType() == Material.IRON_SWORD) {
            //manager.castPlayerSwordAbility(player);
            playerClass.castSwordAbility();
        }
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        if (event.isSneaking())
            return; // Cancel if toggling crouch on, only fire when standing back up
        Player player = event.getPlayer();
        BaseGameClass gameClass = manager.getClassForPlayer(player);
        if (gameClass == null)
            return;

        long time = System.nanoTime();
        long crouchInterval = time - gameClass.lastCrouchTime;
        if (crouchInterval < ULTIMATE_INTERVAL) {
            gameClass.castUltimate();
        }

        gameClass.lastCrouchTime = time;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player damager = (Player)event.getDamager();
            BaseGameClass gameClass = manager.getClassForPlayer(damager);
            if (gameClass != null)
                gameClass.applyOnHitEffects(event);
        }
    }
}
