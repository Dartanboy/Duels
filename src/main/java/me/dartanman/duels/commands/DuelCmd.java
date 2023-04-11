package me.dartanman.duels.commands;

import me.dartanman.duels.Duels;
import me.dartanman.duels.commands.subcommands.HelpSubCmd;
import me.dartanman.duels.commands.subcommands.JoinSubCmd;
import me.dartanman.duels.commands.subcommands.ListArenasSubCmd;
import me.dartanman.duels.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DuelCmd implements CommandExecutor
{

    private final Duels plugin;

    public DuelCmd(Duels plugin)
    {
        this.plugin = plugin;
        new JoinSubCmd(plugin);
        new ListArenasSubCmd(plugin);
        new HelpSubCmd(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length == 0)
        {
            sender.sendMessage("Try /duels help");
            return true;
        }
        else
        {
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
            for(SubCommand subCommand : SubCommand.getSubCommands())
            {
                if(subCommand.getSubCommand().equalsIgnoreCase(args[0]))
                {
                    return subCommand.execute(sender, subArgs);
                }
            }
            sender.sendMessage("Unknown duels command");
            return true;
        }
    }
}
