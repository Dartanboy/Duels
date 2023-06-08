package me.dartanman.duels.commands.subcommands;

import me.dartanman.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpDuelsSubCmd extends DuelsSubCommand
{

    public HelpDuelsSubCmd(Duels plugin)
    {
        super(plugin, "help");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("duels.help"))
        {
            noPerm(sender);
            return true;
        }
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
