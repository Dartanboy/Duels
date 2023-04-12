package me.dartanman.duels.commands.subcommands.duel;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.game.kits.Kit;
import me.dartanman.duels.game.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

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
                    for(Kit kit : plugin.getKitManager().getKitList())
                    {
                        player.sendMessage("- " + kit.getName());
                    }
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
                    for(Kit kit : plugin.getKitManager().getKitList())
                    {
                        if(kit.getName().equalsIgnoreCase(kitName))
                        {
                            player.sendMessage(ChatColor.RED + "A kit with that name already exists!");
                            return true;
                        }
                    }

                    plugin.getKitManager().createKit(kitName, player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("Messages.Kit-Created"))
                                    .replace("<kit_name>", kitName)));
                    return true;
                }
                else if (args[0].equalsIgnoreCase("delete"))
                {
                    String kitName = args[1];
                    plugin.getKitManager().deleteKit(kitName);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("Messages.Kit-Deleted"))
                                    .replace("<kit_name>", kitName)));
                    return true;
                }
                else if (args[0].equalsIgnoreCase("select"))
                {
                    String kitName = args[1];
                    Kit kit = plugin.getKitManager().getKit(kitName);
                    if(kit != null)
                    {
                        KitManager.selectKit(player, kit.getName());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Objects.requireNonNull(plugin.getConfig().getString("Messages.Kit-Selected"))
                                        .replace("<kit_name>", kit.getName())));
                    }
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
