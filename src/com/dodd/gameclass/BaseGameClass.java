package com.dodd.gameclass;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class BaseGameClass {
    protected float ultimateProgress;
    protected Player player;
    protected float ultimateCap = 100;

    Material[] swordAbilityWands;
    long lastCrouchTime = 0;


    public BaseGameClass(Player player) {
        this.player = player;
        this.ultimateProgress = 100;
    }

    public void addUltimateProgress(float progress) {
        float newProgress = Math.min(ultimateProgress + progress, ultimateCap);
        if (ultimateProgress < ultimateCap && newProgress == ultimateCap)
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        ultimateProgress = newProgress;
        float percentage = ultimateProgress / ultimateCap;
        player.sendExperienceChange(percentage, 0);
    }

    public abstract void castSwordAbility();
    public abstract void castProjectileAbility();
    public abstract void castUltimate();

    public abstract void applyOnHitEffects(EntityDamageByEntityEvent event);
}
