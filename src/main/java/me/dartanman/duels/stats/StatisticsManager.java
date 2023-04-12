package me.dartanman.duels.stats;

import me.dartanman.duels.Duels;
import me.dartanman.duels.stats.db.DatabaseType;
import me.dartanman.duels.stats.db.StatisticsDatabase;
import me.dartanman.duels.stats.db.impl.StatisticsDatabaseYAML;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StatisticsManager
{

    private final Duels plugin;
    private FileConfiguration statsFile = new YamlConfiguration();
    private File statsF;
    private final StatisticsDatabase statsDB;

    public StatisticsManager(Duels plugin, DatabaseType dbType)
    {
        this.plugin = plugin;
        this.statsDB = connectToDatabase(dbType);
    }

    private StatisticsDatabase connectToDatabase(DatabaseType dbType)
    {
        if(dbType == DatabaseType.YAML)
        {
            Bukkit.getLogger().info("[Duels] Using YAML File storage for Statistics");
            createStatsConfig();
            saveStatsConfig();
            return new StatisticsDatabaseYAML(statsFile);
        }
        else
        {
            // TODO: MySQL (and maybe MongoDB)
            return null;
        }
    }

    public StatisticsDatabase getStatsDB()
    {
        return statsDB;
    }

    public FileConfiguration getStatsConfig()
    {
        return statsFile;
    }

    public void saveStatsConfig()
    {
        try
        {
            statsFile.save(statsF);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Bukkit.getLogger().warning("[Duels] Failed to save statistics.yml");
        }
    }

    private void createStatsConfig()
    {
        statsF = new File(plugin.getDataFolder(), "statistics.yml");
        saveRes(statsF, "statistics.yml");
        try
        {
            statsFile.load(statsF);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
            Bukkit.getLogger().warning("[Duels] Failed to create statistics.yml");
        }
    }

    private void saveRes(File file, String name)
    {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }
    }

}
