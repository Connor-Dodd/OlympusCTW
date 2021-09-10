package com.dodd.gameclass;


import com.dodd.SoundUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Berserker extends BaseGameClass {


    public Berserker(Player player) {
        super(player);
    }

    @Override
    public void castSwordAbility() {

    }

    @Override
    public void castProjectileAbility() {

    }

    @Override
    public void castUltimate() {
        SoundUtil.playSoundForAll(Sound.ENTITY_GHAST_SCREAM, player.getLocation(), 1f, 0.5f);
    }

    @Override
    public void applyOnHitEffects(EntityDamageByEntityEvent event) {

    }
}
