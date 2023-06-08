package me.dartanman.duels.listeners;

import me.dartanman.duels.Duels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class StatsListener implements Listener
{

    private final Duels plugin;

    public StatsListener(Duels plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();
        String name = event.getPlayer().getName();
        if(!plugin.getStatisticsManager().getStatsDB().isRegistered(uuid))
        {
            plugin.getStatisticsManager().getStatsDB().registerNewPlayer(uuid, name);
        }
    }

}
