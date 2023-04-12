package me.dartanman.duels.stats.db;

import java.util.UUID;

public interface StatisticsDatabase
{

    public int getWins(UUID uuid);

    public int getLosses(UUID uuid);

    public int getKills(UUID uuid);

    public int getDeaths(UUID uuid);

    public void setWins(UUID uuid, int wins);

    public void setLosses(UUID uuid, int losses);

    public void setKills(UUID uuid, int kills);

    public void setDeaths(UUID uuid, int deaths);

    public UUID getUUID(String playerName);

}
