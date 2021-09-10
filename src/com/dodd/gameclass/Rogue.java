package com.dodd.gameclass;

import com.dodd.MCTWPlugin;
import com.dodd.SoundUtil;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;

public class Rogue extends BaseGameClass {
    private ArmorStand knife = null;
    private BukkitRunnable knifeUpdateLoop = null;
    private boolean stealthed = false;

    public Rogue(Player player) {
        super(player);
        ultimateCap = 120;
    }
    @Override
    public void castSwordAbility() {
        player.sendMessage(ChatColor.RED + "WHOOSH! You just threw a sword");
        throwKnife();
    }

    @Override
    public void castProjectileAbility() {

    }

    @Override
    public void castUltimate() {
        if (ultimateProgress != ultimateCap)
            return;
        ultimateProgress = 0;

        SoundUtil.playSoundForAll(Sound.ENTITY_WITCH_CELEBRATE, player.getLocation(), 2f, 0.5f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 200, 3, 1, 3);

        startStealth();
    }

    Collection<PotionEffect> ultimateOnHitEffects = Arrays.asList(new PotionEffect[] {
        new PotionEffect(PotionEffectType.SLOW, 50, 3, true),
        new PotionEffect(PotionEffectType.BLINDNESS, 80, 1, false)
    });
    @Override
    public void applyOnHitEffects(EntityDamageByEntityEvent event) {
        if (stealthed) {
            stealthed = false;

            event.setDamage(event.getDamage() * 2);
            LivingEntity entity = (LivingEntity)event.getEntity();
            entity.addPotionEffects(ultimateOnHitEffects);
            entity.getWorld().playEffect(entity.getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, 152);

            endStealth();
        }
    }

    public void throwKnife() {
        SoundUtil.playSoundForAll(Sound.ENTITY_WITCH_THROW, player.getLocation(), 1f, 0.5f);

        Snowball snowball = player.launchProjectile(Snowball.class);
        return;
//
//        Vector dir = player.getEyeLocation().getDirection();
//        knife = player.getWorld().spawn(player.getLocation().add(dir), ArmorStand.class);
//        knife.getEquipment().setHelmet(new ItemStack(Material.IRON_SWORD));
//        knife.setVisible(false);
//        knife.setCanPickupItems(false);
//        knife.setGravity(false);
//
//        knifeUpdateLoop = new BukkitRunnable() {
//            @Override
//            public void run() {
//                Location newLocation = knife.getLocation().add(dir);
//                knife.teleport(newLocation);
//
//                if (newLocation.getBlock().getType() != Material.AIR) {
//                    knifeUpdateLoop.cancel();
//                    knife.remove();
//                    return;
//                }
//                if (knife.getLocation().distance(player.getLocation()) > 20) {
//                    knifeUpdateLoop.cancel();
//                    knife.remove();
//                    return;
//                }
//
//            }
//        };
//        knifeUpdateLoop.runTaskTimer(MCTWPlugin.current, 1L, 1L);

//
//        swordCooldown = true;
//        swordTimer = waitTime;
//        swordTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
//                new ProjectileTimer(plugin, p, Material.IRON_SWORD), 1, 20);
    }

    private static final int ULTIMATE_DURATION = 120;
    private Collection<PotionEffect> ultimateEffects = Arrays.asList(new PotionEffect[]{
            new PotionEffect(PotionEffectType.INVISIBILITY, ULTIMATE_DURATION, 1, false),
            new PotionEffect(PotionEffectType.SPEED, ULTIMATE_DURATION, 2, false)
    });
    public void startStealth() {
        //Set flag for next hit
        stealthed = true;

        //De-target player from an mobs on them
        player.getWorld().getEntities().stream()
                .filter(ent -> ent instanceof Creature)
                .map(ent -> (Creature) ent)
                .filter(mob -> mob.getTarget() != null)
                .filter(mob -> player.getUniqueId().equals(mob.getTarget().getUniqueId()))
                .forEach(mob -> mob.setTarget(null));

        //Allow the player to run through mobs
        player.setCollidable(false);
        player.addPotionEffects(ultimateEffects);

        for (String playerName : MCTWPlugin.current.getTeamManager().getTeamEnemies(player)) {
            Player enemy = Bukkit.getPlayer(playerName);
            enemy.hidePlayer(MCTWPlugin.current, player);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                endStealth();
            }
        }.runTaskLater(MCTWPlugin.current, 120L);
    }

    public void endStealth() {
        if (!stealthed)
            return;
        stealthed = false;

        //Only play end sound for player
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);

        player.setCollidable(true);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.SPEED);

        //Re-show player
        for (Player viewer : player.getWorld().getPlayers())
            viewer.showPlayer(MCTWPlugin.current, player);
    }
}
