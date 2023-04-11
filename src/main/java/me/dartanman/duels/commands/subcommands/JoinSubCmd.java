package me.dartanman.duels.commands.subcommands;

import me.dartanman.duels.Duels;
import me.dartanman.duels.GameState;
import me.dartanman.duels.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class JoinSubCmd extends SubCommand
{

    public JoinSubCmd(Duels plugin)
    {
        super(plugin, "join");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(sender instanceof Player player)
        {
            if(args.length == 0)
            {
                Arena available = findFirstArena();
                if(available == null)
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("Messages.No-Available-Arena"))));
                    return true;
                }
                else
                {
                    available.addPlayer(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Messages.Joined-Arena"))
                                    .replace("<arena_name>", available.getName()))));
                    return true;
                }
            }
            else
            {
                incorrectArgs(sender, "/duels join");
                return true;
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You must be a Player to do that!");
            return false;
        }
    }

    private Arena findFirstArena()
    {
        for(Arena arena : plugin.getArenaManager().getArenaList())
        {
            if(arena.getGameState() == GameState.IDLE)
            {
                return arena;
            }
        }
        return null;
    }
}
