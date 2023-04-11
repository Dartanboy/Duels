package me.dartanman.duels;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ArenaListener implements Listener
{

    private final Duels plugin;

    public ArenaListener(Duels plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if(pdc.has(new NamespacedKey(plugin, "world"), PersistentDataType.STRING))
        {
            String worldName = pdc.get(new NamespacedKey(plugin, "world"), PersistentDataType.STRING);
            double x = pdc.get(new NamespacedKey(plugin, "x"), PersistentDataType.DOUBLE);
            double y = pdc.get(new NamespacedKey(plugin, "x"), PersistentDataType.DOUBLE);
            double z = pdc.get(new NamespacedKey(plugin, "x"), PersistentDataType.DOUBLE);
            assert worldName != null;
            World world = Bukkit.getWorld(worldName);
            player.teleport(new Location(world, x, y, z));
            pdc.remove(new NamespacedKey(plugin, "world"));
            pdc.remove(new NamespacedKey(plugin, "x"));
            pdc.remove(new NamespacedKey(plugin, "y"));
            pdc.remove(new NamespacedKey(plugin, "z"));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Arena arena = plugin.getArenaManager().getArena(player);
        if(arena != null)
        {
            arena.removePlayer(player);
            PlayerRestoration.restorePlayer(player, true);
        }
    }

}
