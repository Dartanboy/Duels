package me.dartanman.duels.listeners;

import me.dartanman.duels.Duels;
import me.dartanman.duels.game.GameState;
import me.dartanman.duels.game.arenas.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameListener implements Listener
{

    private final Duels plugin;

    public GameListener(Duels plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof Player player)
        {
            Arena arena = plugin.getArenaManager().getArena(player);
            if(arena != null)
            {
                if(arena.getGameState() == GameState.PLAYING)
                {
                    double dmg = event.getFinalDamage();
                    double health = player.getHealth();
                    if(health - dmg <= 0)
                    {
                        arena.getGame().kill(player);
                    }
                }
            }
        }
    }

}
