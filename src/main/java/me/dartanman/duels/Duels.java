package me.dartanman.duels;

import me.dartanman.duels.game.kits.KitManager;
import me.dartanman.duels.listeners.ArenaListener;
import me.dartanman.duels.game.arenas.ArenaManager;
import me.dartanman.duels.commands.DuelCmd;
import me.dartanman.duels.listeners.GameListener;
import me.dartanman.duels.listeners.StatsListener;
import me.dartanman.duels.stats.StatisticsManager;
import me.dartanman.duels.stats.db.DatabaseType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Duels extends JavaPlugin
{

    /*
    **This is Duels 3.2.0 Public Dev Build 3**
I would not recommend using this on a Production server, as it is still missing a few smaller features, and _might_ have some bugs, as I have not tested this as rigorously as I would like to yet. That being said, it _should be_ rather stable. Perhaps even more stable than the current production-level version (Duels 3.1.3).

**SOME STEPS YOU NEED TO TAKE TO UPGRADE FROM 3.1.3 (or lower)**
1. Stop your server.
2. Delete your old config.yml and your old Duels JAR file.
3. Add the new Duels JAR file.
4. If you wish to load the old tracked wins into the new statistics system, run the command /
     */

    private ArenaManager arenaManager;
    private KitManager kitManager;
    private StatisticsManager statisticsManager;

    @Override
    public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.arenaManager = new ArenaManager(this);
        this.kitManager = new KitManager(this);
        setupStatisticsManager();

        getCommand("duel").setExecutor(new DuelCmd(this));

        getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        getServer().getPluginManager().registerEvents(new StatsListener(this), this);
    }

    private void setupStatisticsManager()
    {
        String storageType = getConfig().getString("Statistics.Storage-Type");
        assert storageType != null;
        if(storageType.equalsIgnoreCase("sql") || storageType.equalsIgnoreCase("mysql"))
        {
            // This will be supported in the future
            Bukkit.getLogger().info("[Duels] SQL is not yet supported. Using YAML...");
        }
        this.statisticsManager = new StatisticsManager(this, DatabaseType.YAML);
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
