package me.dartanman.duels.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaListener implements Listener
{

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        // TODO: Remove from arena
    }

}
