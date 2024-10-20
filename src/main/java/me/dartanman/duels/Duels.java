package me.dartanman.duels;

import de.tr7zw.changeme.nbtapi.NBT;
import me.dartanman.duels.commands.DuelTabCompleter;
import me.dartanman.duels.game.kits.KitManager;
import me.dartanman.duels.listeners.ArenaListener;
import me.dartanman.duels.game.arenas.ArenaManager;
import me.dartanman.duels.commands.DuelCmd;
import me.dartanman.duels.listeners.GameListener;
import me.dartanman.duels.listeners.PlayerListener;
import me.dartanman.duels.listeners.StatsListener;
import me.dartanman.duels.stats.StatisticsManager;
import me.dartanman.duels.stats.db.DatabaseType;
import me.dartanman.duels.utils.KitChecker;
import me.dartanman.duels.utils.PapiHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Duels extends JavaPlugin
{

    private ArenaManager arenaManager;
    private KitManager kitManager;
    private StatisticsManager statisticsManager;

    @Override
    public void onEnable()
    {
        int pluginId = 12801;
        Metrics metrics = new Metrics(this, pluginId);

        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling Duels!");
            getPluginLoader().disablePlugin(this);
            return;
        }

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            new PapiHook(this).register();
        }
        else
        {
            Bukkit.getLogger().warning("PlaceholderAPI not found! The plugin will still work, but you can't make leaderboards outside of the built-in leaderboard.");
        }

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.arenaManager = new ArenaManager(this);
        this.kitManager = new KitManager(this);
        setupStatisticsManager();

        getCommand("duel").setExecutor(new DuelCmd(this));
        getCommand("duel").setTabCompleter(new DuelTabCompleter(this));

        getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        getServer().getPluginManager().registerEvents(new StatsListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        KitChecker.kitCheck(null);
    }

    private void setupStatisticsManager()
    {
        String storageType = getConfig().getString("Statistics.Storage-Type");
        assert storageType != null;
        if(storageType.equalsIgnoreCase("sql") || storageType.equalsIgnoreCase("mysql"))
        {
            this.statisticsManager = new StatisticsManager(this, DatabaseType.SQL);
        }
        else if (storageType.equalsIgnoreCase("yaml") || storageType.equalsIgnoreCase("yml"))
        {
            this.statisticsManager = new StatisticsManager(this, DatabaseType.YAML);
        }
        else
        {
            Bukkit.getLogger().warning("Duels does not recognize the Storage Option '" + storageType + "'. Opting for YAML (flat-file) storage.");
            this.statisticsManager = new StatisticsManager(this, DatabaseType.YAML);
        }
    }

    public ArenaManager getArenaManager()
    {
        return arenaManager;
    }

    public KitManager getKitManager()
    {
        return kitManager;
    }

    public StatisticsManager getStatisticsManager()
    {
        return statisticsManager;
    }

}

