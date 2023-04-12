package me.dartanman.duels.game;

import me.dartanman.duels.game.arenas.Arena;
import me.dartanman.duels.stats.db.StatisticsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Game
{
    private final Arena arena;

    public Game(Arena arena)
    {
        this.arena = arena;
    }

    public void start()
    {
        arena.setGameState(GameState.PLAYING);

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

        arena.getKitManager().giveKit(playerOne);
        arena.getKitManager().giveKit(playerTwo);
    }

    public void kill(Player player)
    {
        Player playerOne = Bukkit.getPlayer(arena.getPlayerOne());
        Player playerTwo = Bukkit.getPlayer(arena.getPlayerTwo());

        assert playerOne != null;
        assert playerTwo != null;

        StatisticsDatabase db = arena.getStatisticsDatabase();

        if(playerOne.getUniqueId().equals(player.getUniqueId()))
        {
            // playerTwo wins
            arena.sendMessage(playerTwo.getName() + " wins!");
            UUID winnerUUID = playerTwo.getUniqueId();
            UUID loserUUID = playerOne.getUniqueId();
            db.setWins(winnerUUID, db.getWins(winnerUUID));
            db.setLosses(loserUUID, db.getLosses(loserUUID));
        }
        else
        {
            // playerOne wins
            arena.sendMessage(playerOne.getName() + " wins!");
            UUID winnerUUID = playerOne.getUniqueId();
            UUID loserUUID = playerTwo.getUniqueId();
            db.setWins(winnerUUID, db.getWins(winnerUUID));
            db.setLosses(loserUUID, db.getLosses(loserUUID));
        }

        arena.reset();
    }
}
