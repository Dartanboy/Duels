package me.dartanman.duels.commands.subcommands.arena;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.game.arenas.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class ListArenasDuelsSubCmd extends DuelsSubCommand
{

    public ListArenasDuelsSubCmd(Duels plugin)
    {
        super(plugin, "listarenas");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(args.length == 0)
        {
            for(Arena arena : plugin.getArenaManager().getArenaList())
            {
                int id = arena.getId();
                String name = arena.getName();
                String state = arena.getGameState().name();
                String message = ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Arena-List-Format"))
                                .replace("<id>", id + "")
                                .replace("<arena_name>", name)
                                .replace("<state>", state));
                sender.sendMessage(message);
            }
            return true;
        }
        else
        {
            incorrectArgs(sender, "/duels listarenas");
            return true;
        }
    }
}
