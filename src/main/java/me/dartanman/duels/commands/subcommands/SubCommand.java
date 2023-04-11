package me.dartanman.duels.commands.subcommands;

import me.dartanman.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SubCommand
{

    protected Duels plugin;
    protected String subCommand;
    protected String[] args;

    private static final List<SubCommand> subCommands = new ArrayList<>();

    public static List<SubCommand> getSubCommands()
    {
        return subCommands;
    }

    public SubCommand(Duels plugin, String subCommand)
    {
        this.plugin = plugin;
        this.subCommand = subCommand;
        subCommands.add(this);
    }

    protected void unknownCommand(CommandSender sender)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(plugin.getConfig().getString("Messages.Unknown-Command"))));
    }

    protected void incorrectArgs(CommandSender sender, String suggestion)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Messages.Incorrect-Args")).replace("<suggestion>", suggestion))));
    }

    public String getSubCommand()
    {
        return subCommand;
    }

    public abstract boolean execute(CommandSender sender, String[] args);

}
