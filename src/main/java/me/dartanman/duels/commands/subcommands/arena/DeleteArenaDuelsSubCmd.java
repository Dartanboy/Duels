package me.dartanman.duels.commands.subcommands.arena;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.game.arenas.Arena;
import me.dartanman.duels.game.arenas.ArenaConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class DeleteArenaDuelsSubCmd extends DuelsSubCommand
{
    public DeleteArenaDuelsSubCmd(Duels plugin)
    {
        super(plugin, "deletearena");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("duels.deletearena"))
        {
            noPerm(sender);
            return true;
        }
        if(sender instanceof Player player)
        {
            if(args.length == 1)
            {
                int id;
                try
                {
                    id = Integer.parseInt(args[0]);
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Messages.Invalid-Arena-Id"))
                                    .replace("<arg>", args[0]))));
                    return true;
                }

                Arena arena = plugin.getArenaManager().getArena(id);
                if(arena == null)
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Messages.Invalid-Arena-Id"))
                                    .replace("<arg>", args[0]))));
                    return true;
                }

                plugin.getArenaManager().delete(id);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Arena-Deleted"))
                                .replace("<arena_name>", args[0])));
                return true;
            }
            else
            {
                incorrectArgs(sender, "/duels deletearena <arena name>");
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
