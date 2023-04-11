package me.dartanman.duels.commands.subcommands;

import me.dartanman.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitsDuelsSubCmd extends DuelsSubCommand
{
    public KitsDuelsSubCmd(Duels plugin)
    {
        super(plugin, "kits");
    }

    @Override
    public boolean is(String string)
    {
        return string.equalsIgnoreCase("kits") || string.equalsIgnoreCase("kit");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(sender instanceof Player player)
        {
            if(args.length == 1)
            {
                if(args[0].equalsIgnoreCase("list"))
                {
                    // TODO: list kits
                    return true;
                }
                else
                {
                    incorrectArgs(player, "/duels help");
                    return true;
                }
            }
            else if (args.length == 2)
            {
                if(args[0].equalsIgnoreCase("create"))
                {
                    String kitName = args[1];
                    // TODO: Create a kit
                    return true;
                }
                else if (args[0].equalsIgnoreCase("delete"))
                {
                    String kitName = args[1];
                    // TODO: Delete a kit
                    return true;
                }
                else if (args[0].equalsIgnoreCase("select"))
                {
                    String kitName = args[1];
                    // TODO: Choose a kit
                    return true;
                }
                else
                {
                    incorrectArgs(player, "/duels kits <create/delete/select> <kit name>");
                    return true;
                }
            }
            else
            {
                incorrectArgs(player, "/duels help");
                return true;
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You must be a Player to do that!");
            return false;
        }
    }
}
