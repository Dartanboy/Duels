package me.dartanman.duels.commands.subcommands.arena;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.DuelsSubCommand;
import me.dartanman.duels.game.arenas.ArenaConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class CreateArenaDuelsSubCmd extends DuelsSubCommand
{
    public CreateArenaDuelsSubCmd(Duels plugin)
    {
        super(plugin, "createarena");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args)
    {
        if(sender instanceof Player player)
        {
            if(args.length == 1)
            {
                UUID uuid = player.getUniqueId();
                String arenaName = args[0];
                int id = plugin.getArenaManager().getNextId();
                ArenaConfig arenaConfig = new ArenaConfig(id, arenaName);
                ArenaConfig.creationMap.put(uuid, arenaConfig);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Arena-Created"))
                                .replace("<arena_name>", args[0])));
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
