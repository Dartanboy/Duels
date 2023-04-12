package me.dartanman.duels.stats.db.impl;

import me.dartanman.duels.stats.db.StatisticsDatabase;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.UUID;

public class StatisticsDatabaseYAML implements StatisticsDatabase
{

    private FileConfiguration statsConfig;

    public StatisticsDatabaseYAML(FileConfiguration statsConfig)
    {
        this.statsConfig = statsConfig;
    }

    @Override
    public int getWins(UUID uuid)
    {
        return statsConfig.getInt("Statistics." + uuid.toString() + ".Wins");
    }

    @Override
    public int getLosses(UUID uuid)
    {
        return statsConfig.getInt("Statistics." + uuid.toString() + ".Losses");
    }

    @Override
    public int getKills(UUID uuid)
    {
        return statsConfig.getInt("Statistics." + uuid.toString() + ".Kills");
    }

    @Override
    public int getDeaths(UUID uuid)
    {
        return statsConfig.getInt("Statistics." + uuid.toString() + ".Deaths");
    }

    @Override
    public void setWins(UUID uuid, int wins)
    {
        statsConfig.set("Statistics." + uuid.toString() + ".Wins", wins);
    }

    @Override
    public void setLosses(UUID uuid, int losses)
    {
        statsConfig.set("Statistics." + uuid.toString() + ".Losses", losses);
    }

    @Override
    public void setKills(UUID uuid, int kills)
    {
        statsConfig.set("Statistics." + uuid.toString() + ".Kills", kills);
    }

    @Override
    public void setDeaths(UUID uuid, int deaths)
    {
        statsConfig.set("Statistics." + uuid.toString() + ".Deaths", deaths);
    }

    @Override
    public UUID getUUID(String playerName)
    {
        for(String uuidStr : Objects.requireNonNull(statsConfig.getConfigurationSection("Statistics")).getKeys(false))
        {
            if(Objects.requireNonNull(statsConfig.getString("Statistics." + uuidStr + ".Last-Known-Name")).equalsIgnoreCase(playerName))
            {
                return UUID.fromString(uuidStr);
            }
        }
        return null;
    }
}
