package me.dartanman.duels.commands.subcommands.arena;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.game.arenas.ArenaConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class FinishArenaDuelsSubCmd extends DuelsSubCommand
{
    public FinishArenaDuelsSubCmd(Duels plugin)
    {
        super(plugin, "finisharena");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(sender instanceof Player player)
        {
            if(args.length == 0)
            {
                UUID uuid = player.getUniqueId();
                ArenaConfig arenaConfig = ArenaConfig.creationMap.get(uuid);

                if(arenaConfig == null)
                {
                    player.sendMessage(ChatColor.RED + "You must do " + ChatColor.YELLOW +
                            "/duels createarena <arena name> " +
                            ChatColor.RED + "before doing that!");
                    return true;
                }

                if(!arenaConfig.isFinished())
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("Messages.Arena-Not-Finished"))));
                    return true;
                }

                plugin.getArenaManager().save(arenaConfig);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Arena-Finished"))));
                return true;
            }
            else
            {
                incorrectArgs(sender, "/duels finisharena");
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
