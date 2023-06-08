package me.dartanman.duels.commands.subcommands.stats;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
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

public class LoadStatsDuelsSubCmd extends DuelsSubCommand
{
    public LoadStatsDuelsSubCmd(Duels plugin)
    {
        super(plugin, "loadoldstats");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("duels.admin"))
        {
            noPerm(sender);
            return true;
        }

        File file = plugin.getDataFolder();
        File[] subFiles = file.listFiles();
        assert subFiles != null;
        for(File subFile : subFiles)
        {
            if(subFile.getName().equals("Stats.yml"))
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
                sender.sendMessage(ChatColor.GREEN + "Loading old statistics from Stats.yml into the new statistics system. This may take awhile and cause short-term lag depending on how many players you have...");
                for(String uuidStr : Objects.requireNonNull(config.getConfigurationSection("Wins")).getKeys(false))
                {
                    UUID uuid = UUID.fromString(uuidStr);
                    int wins = config.getInt("Wins." + uuidStr);
                    plugin.getStatisticsManager().getStatsDB().setWins(uuid, wins);
                }
                sender.sendMessage(ChatColor.GREEN + "Statistics loading complete!");
                return true;
            }
        }
        return true;
    }
}
