package me.dartanman.duels.stats.db;

import java.util.HashMap;
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

    public void registerNewPlayer(UUID uuid, String name);

    public boolean isRegistered(UUID uuid);

    public UUID getUUID(String playerName);

    public String getLastKnownName(UUID uuid);

    /**
     * May or may not be sorted depending on implementation of specific Databases
     */
    public HashMap<UUID, Integer> getTopTenWins();

    /**
     * May or may not be sorted depending on implementation of specific Databases
     */
    public HashMap<UUID, Integer> getTopTenKills();

}
