package me.dartanman.duels;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ArenaManager
{

    private final Duels plugin;
    private final List<Arena> arenaList;

    public ArenaManager(Duels plugin)
    {
        this.plugin = plugin;
        this.arenaList = new ArrayList<>();

        loadArenas();
    }

    private void loadArenas()
    {
        FileConfiguration config = plugin.getConfig();
        if(!config.contains("Arenas"))
        {
            return;
        }
        for(String arenaIdStr : Objects.requireNonNull(config.getConfigurationSection("Arenas")).getKeys(false))
        {
            String path = "Arenas." + arenaIdStr;
            int id;
            try
            {
                id = Integer.parseInt(arenaIdStr);
            }
            catch (NumberFormatException e)
            {
                Bukkit.getLogger().severe("Failed to parse integer '" + arenaIdStr + "' from config.yml (arena id)");
                continue;
            }
            String name = plugin.getConfig().getString(path + ".Name");
            Location spawnOne = ConfigUtils.getLocation(path + ".Spawn-One");
            Location spawnTwo = ConfigUtils.getLocation(path + ".Spawn-Two");
            Location lobby = ConfigUtils.getLocation(path + ".Lobby");
            int countdownSeconds = plugin.getConfig().getInt(path + ".Countdown-Seconds");
            Arena arena = new Arena(plugin, id, name, spawnOne, spawnTwo, lobby, countdownSeconds);
            arenaList.add(arena);
        }
    }

    public Arena getArena(Player player)
    {
        UUID uuid = player.getUniqueId();
        for(Arena arena : arenaList)
        {
            if(arena.getPlayers().contains(uuid))
            {
                return arena;
            }
        }
        return null;
    }

    public List<Arena> getArenaList()
    {
        return arenaList;
    }

}
