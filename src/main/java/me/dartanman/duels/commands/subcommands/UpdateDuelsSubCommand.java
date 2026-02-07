package me.dartanman.duels.commands.subcommands;

import me.dartanman.duels.Duels;
import me.dartanman.duels.utils.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class UpdateDuelsSubCommand extends DuelsSubCommand
{

    public UpdateDuelsSubCommand(Duels plugin)
    {
        super(plugin, "update");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if (!sender.hasPermission("duels.admin"))
        {
            noPerm(sender);
            return true;
        }
        if (args.length == 0)
        {
            UpdateChecker.updateCheck(this.plugin, sender, true);
            return true;
        }
        else
        {
            incorrectArgs(sender, "/duels help");
            return true;
        }
    }
}
