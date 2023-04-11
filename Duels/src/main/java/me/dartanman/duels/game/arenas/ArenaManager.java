package me.dartanman.duels.game.arenas;

import me.dartanman.duels.Duels;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ArenaManager
{

    private final Duels plugin;

    public ArenaManager(Duels plugin)
    {
        this.plugin = plugin;
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
            // TODO
        }
    }

}
