package me.dartanman.duels.listeners;

import me.dartanman.duels.utils.KitChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission("duels.admin")) {
            KitChecker.kitCheck(player);
        }
    }
}
