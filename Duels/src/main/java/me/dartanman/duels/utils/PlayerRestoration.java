package me.dartanman.duels.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlayerRestoration
{

    private static final HashMap<UUID, SavedPlayerInfo> savedInfo = new HashMap<>();

    public static void savePlayer(Player player)
    {
        new SavedPlayerInfo(player);
    }

    public static void restorePlayer(Player player)
    {
        if(savedInfo.containsKey(player.getUniqueId()))
        {
            savedInfo.get(player.getUniqueId()).restore();
        }
        savedInfo.remove(player.getUniqueId());
    }

    private static class SavedPlayerInfo
    {
        private UUID uuid;
        private Location location;
        private GameMode gameMode;
        private ItemStack[] inventoryContents;
        private ItemStack[] armorContents;
        private int xpLevel;
        private double health;
        private double maxHealth;
        private Collection<PotionEffect> potionEffects;

        protected SavedPlayerInfo(Player player)
        {
            if(player == null)
            {
                return;
            }

            this.uuid = player.getUniqueId();
            this.location = player.getLocation();
            this.gameMode = player.getGameMode();
            this.inventoryContents = player.getInventory().getContents();
            this.armorContents = player.getInventory().getArmorContents();
            this.xpLevel = player.getLevel();
            this.health = player.getHealth();
            this.maxHealth = player.getMaxHealth();
            this.potionEffects = player.getActivePotionEffects();

            savedInfo.put(uuid, this);
        }

        protected Player getPlayer()
        {
            return Bukkit.getPlayer(uuid);
        }

        protected void restore()
        {
            getPlayer().teleport(location);
            getPlayer().setGameMode(gameMode);
            getPlayer().getInventory().setContents(inventoryContents);
            getPlayer().getInventory().setArmorContents(armorContents);
            getPlayer().setLevel(xpLevel);
            getPlayer().setMaxHealth(maxHealth);
            getPlayer().setHealth(health);
            for(PotionEffect effect : potionEffects)
            {
                getPlayer().addPotionEffect(effect);
            }
        }
    }

}
