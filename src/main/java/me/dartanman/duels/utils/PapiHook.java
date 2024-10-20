package me.dartanman.duels.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dartanman.duels.Duels;
import me.dartanman.duels.game.arenas.Arena;
import me.dartanman.duels.game.arenas.ArenaManager;
import me.dartanman.duels.stats.db.StatisticsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.UUID;

public class PapiHook extends PlaceholderExpansion
{
    private final Duels plugin;

    public PapiHook(Duels plugin)
    {
        this.plugin = plugin;
    }


    @Override
    public @NotNull String getIdentifier()
    {
        return "duels";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion()
    {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        // This is required or else PlaceholderAPI will unregister the Expansion on reload
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if(params.toLowerCase().startsWith("top_wins_"))
        {
            String[] split = params.split("_");
            String num = split[2];
            int place = -1;
            try
            {
                place = Integer.parseInt(num);
            }
            catch (NumberFormatException e)
            {
                return "Err: Format should be 'duels_top_wins_#' where '#' is a number between 1-10";
            }
            if(place > 10)
            {
                return "Err: Format should be 'duels_top_wins_#' where '#' is a number between 1-10";
            }

            StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
            LinkedHashMap<UUID, Integer> winsMap = MapSortingUtils.sort(db.getTopTenWins());
            int i = 0;
            for(UUID uuid : winsMap.keySet())
            {
                if(i == place-1)
                {
                    return db.getLastKnownName(uuid) + " (" + winsMap.get(uuid) + " wins)";
                }
            }
            return ("N/A");
        }
        else if(params.toLowerCase().startsWith("top_kills_"))
        {
            String[] split = params.split("_");
            String num = split[2];
            int place = -1;
            try
            {
                place = Integer.parseInt(num);
            }
            catch (NumberFormatException e)
            {
                return "Err: Format should be 'duels_top_kills_#' where '#' is a number between 1-10";
            }
            if(place > 10)
            {
                return "Err: Format should be 'duels_top_kills_#' where '#' is a number between 1-10";
            }

            StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
            LinkedHashMap<UUID, Integer> killsMap = MapSortingUtils.sort(db.getTopTenKills());
            int i = 0;
            for(UUID uuid : killsMap.keySet())
            {
                if(i == place-1)
                {
                    return db.getLastKnownName(uuid) + " (" + killsMap.get(uuid) + " kills)";
                }
            }
            return ("N/A");
        }
        else if(params.equalsIgnoreCase("your_kills"))
        {
            StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
            int kills = db.getKills(player.getUniqueId());
            return kills + "";
        }
        else if(params.equalsIgnoreCase("your_wins"))
        {
            StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
            int wins = db.getWins(player.getUniqueId());
            return wins + "";
        } else if(params.equalsIgnoreCase("opponent_name"))
        {
            if(player instanceof Player onlinePlayer) {
                ArenaManager arenaManager = plugin.getArenaManager();
                Arena arena = arenaManager.getArena(onlinePlayer);
                if(arena != null) {
                    Player target;
                    if(arena.getPlayerOne().equals(player.getUniqueId())) {
                        target = Bukkit.getPlayer(arena.getPlayerTwo());
                    } else {
                        target = Bukkit.getPlayer(arena.getPlayerOne());
                    }

                    if (target != null) {
                        return target.getName();
                    } else {
                        return "None";
                    }
                }
            }

        }

        // Placeholder is unknown by the Expansion
        return null;
    }
}
