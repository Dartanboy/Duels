package me.dartanman.duels.utils;

import me.dartanman.duels.Duels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class KitChecker {

    private static Duels plugin;

    private static Duels getPlugin()
    {
        if(plugin == null)
        {
            plugin = JavaPlugin.getPlugin(Duels.class);
        }
        return plugin;
    }

    private static boolean kitIsOld(String path) {
        FileConfiguration config = getPlugin().getConfig();
        for(String line : config.getStringList(path + ".Inventory")) {
            if(line.contains("Count")) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasOldKits() {
        FileConfiguration config = getPlugin().getConfig();
        for(String kitIdStr : Objects.requireNonNull(config.getConfigurationSection("Kits")).getKeys(false))
        {
            String path = "Kits." + kitIdStr;
            if (kitIsOld(path)) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldWarnOfOldKits() {
        String version = Bukkit.getVersion();
        if(version.contains("1.20.6") || version.contains("1.21")) {
            return hasOldKits();
        } else {
            return false;
        }
    }

    public static void kitCheck(Player forWho) {
        if(!shouldWarnOfOldKits()) {
            return;
        }

        if(forWho == null) {
            Bukkit.getLogger().warning("Duels has kits made for Minecraft 1.20.5 or older!");
            Bukkit.getLogger().warning("While the plugin may still work, some kits may be broken.");
            Bukkit.getLogger().warning("You can fix this by re-making your kits!");
        } else {
            forWho.sendMessage(ChatColor.RED + "Duels has kits made for Minecraft 1.20.5 or older!");
            forWho.sendMessage(ChatColor.RED + "While the plugin may still work, some kits may be broken.");
            forWho.sendMessage(ChatColor.RED + "You can fix this by re-making your kits!");
        }
    }

}
