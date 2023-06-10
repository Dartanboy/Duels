package me.dartanman.duels.commands.subcommands.stats;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.stats.db.StatisticsDatabase;
import me.dartanman.duels.stats.db.impl.StatisticsDatabaseSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class StatsFileToSQLCmd extends DuelsSubCommand
{
    public StatsFileToSQLCmd(Duels plugin)
    {
        super(plugin, "filetosql");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("duels.admin"))
        {
            noPerm(sender);
            return true;
        }

        if(!(plugin.getStatisticsManager().getStatsDB() instanceof StatisticsDatabaseSQL))
        {
            sender.sendMessage(ChatColor.RED + "You have not authorized Duels to use a SQL database! Use the config.yml to do so.");
            return true;
        }

        File file = plugin.getDataFolder();
        File[] subFiles = file.listFiles();
        assert subFiles != null;
        for(File subFile : subFiles)
        {
            if(subFile.getName().equals("statistics.yml"))
            {
                Bukkit.getLogger().info(subFile.getName() + " found");
                FileConfiguration config = new YamlConfiguration();
                try
                {
                    config.load(subFile);
                } catch (IOException | InvalidConfigurationException e)
                {
                    throw new RuntimeException(e);
                }
                sender.sendMessage(ChatColor.GREEN + "Loading statistics.yml information into the SQL Database. This may take awhile and cause short-term lag depending on how many players you have...");
                for(String uuidStr : Objects.requireNonNull(config.getConfigurationSection("Statistics")).getKeys(false))
                {
                    UUID uuid = UUID.fromString(uuidStr);
                    String path = "Statistics." + uuid.toString() + ".";
                    String lastKnownName = config.getString(path + "Last-Known-Name");
                    int wins = config.getInt(path + "Wins");
                    int losses = config.getInt(path + "Losses");
                    int kills = config.getInt(path + "Kills");
                    int deaths = config.getInt(path + "Deaths");

                    StatisticsDatabase db = plugin.getStatisticsManager().getStatsDB();
                    if(!db.isRegistered(uuid))
                    {
                        db.registerNewPlayer(uuid, lastKnownName);
                    }
                    db.setWins(uuid, wins);
                    db.setLosses(uuid, losses);
                    db.setKills(uuid, kills);
                    db.setDeaths(uuid, deaths);
                }
                sender.sendMessage(ChatColor.GREEN + "Statistics loading complete!");
                return true;
            }
        }
        return true;
    }
}
