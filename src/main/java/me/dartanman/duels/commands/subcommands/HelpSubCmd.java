package me.dartanman.duels.commands.subcommands;

import me.dartanman.duels.Duels;
import me.dartanman.duels.game.arenas.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class HelpSubCmd extends SubCommand
{

    public HelpSubCmd(Duels plugin)
    {
        super(plugin, "help");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(args.length == 0)
        {
            for(String line : plugin.getConfig().getStringList("Messages.Help-Menu"))
            {
                line = ChatColor.translateAlternateColorCodes('&', line);
                sender.sendMessage(line);
            }
            return true;
        }
        else
        {
            incorrectArgs(sender, "/duels help");
            return true;
        }
    }
}
