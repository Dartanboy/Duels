package me.dartanman.duels.commands.subcommands.arena;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.game.arenas.ArenaConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class SetSpawn1DuelsSubCmd extends DuelsSubCommand
{
    public SetSpawn1DuelsSubCmd(Duels plugin)
    {
        super(plugin, "setspawn1");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("duels.createarena"))
        {
            noPerm(sender);
            return true;
        }
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

                arenaConfig.setSpawnOne(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Spawnpoint set!");
                return true;
            }
            else
            {
                incorrectArgs(sender, "/duels createarena <arena name>");
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
