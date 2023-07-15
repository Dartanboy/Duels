package me.dartanman.duels.commands;

import me.dartanman.duels.Duels;
import me.dartanman.duels.game.kits.Kit;
import me.dartanman.duels.game.kits.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class DuelTabCompleter implements TabCompleter
{

    private Duels plugin;

    public DuelTabCompleter(Duels plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
    {
        List<String> recommend = new ArrayList<>();
        if(args.length == 1)
        {
            recommend.add("help");
            recommend.add("join");
            recommend.add("leave");
            recommend.add("listarenas");
            recommend.add("createarena");
            recommend.add("deletearena");
            recommend.add("setlobby");
            recommend.add("setspawn1");
            recommend.add("setspawn2");
            recommend.add("finisharena");
            recommend.add("kits");
            recommend.add("loadoldstats");
            recommend.add("stats");
        }
        else if (args.length == 2)
        {
            if(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("createarena"))
            {
                return recommend;
            }
            else if (args[0].equalsIgnoreCase("kits"))
            {
                recommend.add("list");
                recommend.add("create");
                recommend.add("delete");
                recommend.add("select");
            }
        }
        else if (args.length == 3)
        {
            if(args[0].equalsIgnoreCase("kits"))
            {
                if(args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("delete")
                || args[1].equalsIgnoreCase("select"))
                {
                    for(Kit kit : plugin.getKitManager().getKitList())
                    {
                        recommend.add(kit.getName());
                    }
                }
            }
        }

        if(recommend.isEmpty())
        {
            return null;
        }
        return recommend;
    }
}
