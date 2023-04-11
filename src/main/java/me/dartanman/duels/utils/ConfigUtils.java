package me.dartanman.duels.utils;

import me.dartanman.duels.Duels;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ConfigUtils
{

    private static Duels plugin;

    private static Duels getPlugin()
    {
        if(plugin == null)
        {
            plugin = JavaPlugin.getPlugin(Duels.class);
        }
        return plugin;
    }

    public static Location getLocation(String path)
    {
        World world = Bukkit.getWorld(Objects.requireNonNull(getPlugin().getConfig().getString(path + ".World")));
        double x = getPlugin().getConfig().getDouble(path + ".X");
        double y = getPlugin().getConfig().getDouble(path + ".Y");
        double z = getPlugin().getConfig().getDouble(path + ".Z");
        float yaw = (float) getPlugin().getConfig().getDouble(path + ".Yaw", 0f);
        float pitch = (float) getPlugin().getConfig().getDouble(path + ".Pitch", 0f);
        return new Location(world, x, y, z, yaw, pitch);
    }

}
