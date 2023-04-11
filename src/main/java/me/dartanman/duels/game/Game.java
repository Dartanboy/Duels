package me.dartanman.duels.game;

import me.dartanman.duels.game.arenas.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Game
{
    private final Arena arena;

    public Game(Arena arena)
    {
        this.arena = arena;
    }

    public void start()
    {
        Player playerOne = Bukkit.getPlayer(arena.getPlayerOne());
        Player playerTwo = Bukkit.getPlayer(arena.getPlayerTwo());

        assert playerOne != null;
        assert playerTwo != null;

        playerOne.teleport(arena.getSpawnOne());
        playerTwo.teleport(arena.getSpawnTwo());

        applyKits();
    }

    private void applyKits()
    {
        Player playerOne = Bukkit.getPlayer(arena.getPlayerOne());
        Player playerTwo = Bukkit.getPlayer(arena.getPlayerTwo());

        assert playerOne != null;
        assert playerTwo != null;

        playerOne.getInventory().clear();
        playerTwo.getInventory().clear();

        // TODO: Give players their kits
        arena.sendMessage("Kits have not *YET* been implemented into the Duels 3.2 update! Sorry!");
    }
}
