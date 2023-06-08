package me.dartanman.duels.commands.subcommands.stats;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.stats.db.StatisticsDatabase;
import me.dartanman.duels.utils.MapSortingUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.UUID;

public class LeaderboardDuelsSubCmd extends DuelsSubCommand
{
    public LeaderboardDuelsSubCmd(Duels plugin)
    {
        super(plugin, "leaderboard");
    }

    @Override
    public boolean is(String string)
    {
        return string.equalsIgnoreCase("leaderboard") || string.equalsIgnoreCase("lb") ||
                string.equalsIgnoreCase("top");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("duels.top"))
        {
            noPerm(sender);
            return true;
        }

        if(args.length == 0)
        {
            // /duels top
            StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
            LinkedHashMap<UUID, Integer> winsMap = MapSortingUtils.sort(db.getTopTenWins());

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(plugin.getConfig().getString("Messages.Leaderboard-Header"))
                            .replace("<type>", "Wins")));
            int place = 1;
            for(UUID uuid : winsMap.keySet())
            {
                String name = db.getLastKnownName(uuid);
                int wins = winsMap.get(uuid);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Leaderboard-Format"))
                                .replace("<place>", String.valueOf(place++))
                                .replace("<player>", name)
                                .replace("<stat>", String.valueOf(wins))));
            }

            return true;
        }
        else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("wins") || args[0].equalsIgnoreCase("win"))
            {
                // /duels top wins
                StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
                LinkedHashMap<UUID, Integer> winsMap = MapSortingUtils.sort(db.getTopTenWins());

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Leaderboard-Header"))
                                .replace("<type>", "Wins")));
                int place = 1;
                for(UUID uuid : winsMap.keySet())
                {
                    String name = db.getLastKnownName(uuid);
                    int wins = winsMap.get(uuid);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("Messages.Leaderboard-Format"))
                                    .replace("<place>", String.valueOf(place++))
                                    .replace("<player>", name)
                                    .replace("<stat>", String.valueOf(wins))));
                }

                return true;
            }
            else if(args[0].equalsIgnoreCase("kills") || args[0].equalsIgnoreCase("kill"))
            {
                // /duels top kills
                StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
                LinkedHashMap<UUID, Integer> killsMap = MapSortingUtils.sort(db.getTopTenKills());

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Leaderboard-Header"))
                                .replace("<type>", "Kills")));
                int place = 1;
                for(UUID uuid : killsMap.keySet())
                {
                    String name = db.getLastKnownName(uuid);
                    int kills = killsMap.get(uuid);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("Messages.Leaderboard-Format"))
                                    .replace("<place>", String.valueOf(place++))
                                    .replace("<player>", name)
                                    .replace("<stat>", String.valueOf(kills))));
                }

                return true;
            }
            else
            {
                incorrectArgs(sender, "/duels top [wins/kills]");
                return true;
            }
        }
        else
        {
            incorrectArgs(sender, "/duels top [wins/kills]");
            return true;
        }
    }
}
