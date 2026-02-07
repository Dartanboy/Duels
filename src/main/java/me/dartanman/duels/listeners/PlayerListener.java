package me.dartanman.duels.listeners;

import me.dartanman.duels.Duels;
import me.dartanman.duels.utils.KitChecker;
import me.dartanman.duels.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission("duels.admin")) {
            KitChecker.kitCheck(player);
            UpdateChecker.updateCheck(JavaPlugin.getPlugin(Duels.class), player, false);
        }
    }
}
