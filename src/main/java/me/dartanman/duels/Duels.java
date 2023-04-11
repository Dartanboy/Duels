package me.dartanman.duels;

import me.dartanman.duels.listeners.ArenaListener;
import me.dartanman.duels.game.arenas.ArenaManager;
import me.dartanman.duels.commands.DuelCmd;
import me.dartanman.duels.listeners.GameListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Duels extends JavaPlugin
{

    private ArenaManager arenaManager;

    @Override
    public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.arenaManager = new ArenaManager(this);

        getCommand("duel").setExecutor(new DuelCmd(this));

        getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
    }

    public ArenaManager getArenaManager()
    {
        return arenaManager;
    }

}
