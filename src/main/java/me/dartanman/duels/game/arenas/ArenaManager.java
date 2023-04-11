package me.dartanman.duels.game.arenas;

import me.dartanman.duels.utils.ConfigUtils;
import me.dartanman.duels.Duels;
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

    public void save(ArenaConfig arenaConfig)
    {
        int id = arenaConfig.getId();
        String idStr = String.valueOf(id);
        String name = arenaConfig.getName();
        Location spawnOne = arenaConfig.getSpawnOne();
        Location spawnTwo = arenaConfig.getSpawnTwo();
        Location lobby = arenaConfig.getLobby();
        int countdownSeconds = arenaConfig.getCountdownSeconds();

        plugin.getConfig().set("Arenas." + idStr + ".Name", name);
        plugin.getConfig().set("Arenas." + idStr + ".Countdown-Seconds", countdownSeconds);
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-One.World", Objects.requireNonNull(spawnOne.getWorld()).getName());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-One.X", spawnOne.getX());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-One.Y", spawnOne.getY());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-One.Z", spawnOne.getZ());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-One.Yaw", spawnOne.getYaw());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-One.Pitch", spawnOne.getPitch());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-Two.World", Objects.requireNonNull(spawnTwo.getWorld()).getName());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-Two.X", spawnTwo.getX());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-Two.Y", spawnTwo.getY());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-Two.Z", spawnTwo.getZ());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-Two.Yaw", spawnTwo.getYaw());
        plugin.getConfig().set("Arenas." + idStr + ".Spawn-Two.Pitch", spawnTwo.getPitch());
        plugin.getConfig().set("Arenas." + idStr + ".Lobby.World", Objects.requireNonNull(lobby.getWorld()).getName());
        plugin.getConfig().set("Arenas." + idStr + ".Lobby.X", lobby.getX());
        plugin.getConfig().set("Arenas." + idStr + ".Lobby.Y", lobby.getY());
        plugin.getConfig().set("Arenas." + idStr + ".Lobby.Z", lobby.getZ());
        plugin.getConfig().set("Arenas." + idStr + ".Lobby.Yaw", lobby.getYaw());
        plugin.getConfig().set("Arenas." + idStr + ".Lobby.Pitch", lobby.getPitch());
        plugin.saveConfig();

        arenaList.add(new Arena(plugin, arenaConfig));
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

    public Arena getArena(int id)
    {
        for(Arena arena : arenaList)
        {
            if(arena.getId() == id)
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

    public int getNextId()
    {
        int max = 0;
        for(Arena arena : arenaList)
        {
            int id = arena.getId();
            if(id > max)
            {
                max = id;
            }
        }
        return max + 1;
    }

}
