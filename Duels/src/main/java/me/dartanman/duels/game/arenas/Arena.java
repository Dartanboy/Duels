package me.dartanman.duels.game.arenas;

import me.dartanman.duels.Duels;
import me.dartanman.duels.game.Countdown;
import me.dartanman.duels.game.Game;
import me.dartanman.duels.game.GameState;
import me.dartanman.duels.utils.PlayerRestoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena
{

    private final int id;
    private final String name;

    private GameState gameState;

    private final List<UUID> players;
    private final Location spawnOne;
    private final Location spawnTwo;
    private final Location lobby;
    private Game game;
    private Countdown countdown;

    public Arena(Duels plugin, int id, String name, Location spawnOne, Location spawnTwo, Location lobby, int countdownSeconds)
    {
        this.id = id;
        this.name = name;

        this.gameState = GameState.IDLE;

        this.spawnOne = spawnOne;
        this.spawnTwo = spawnTwo;
        this.lobby = lobby;
        this.players = new ArrayList<>();
        this.game = new Game(this);
        this.countdown = new Countdown(plugin, this, countdownSeconds);
    }

    /*
     * Gameplay
     */

    public void start()
    {
        countdown.start();
    }

    /*
     * Arena Utilities
     */

    public void sendMessage(String message)
    {
        for(UUID uuid : players)
        {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null)
            {
                player.sendMessage(message);
            }
        }
    }

    /*
     * Players
     */

    public void addPlayer(Player player)
    {
        PlayerRestoration.savePlayer(player);
        players.add(player.getUniqueId());
        player.teleport(lobby);
    }

    public void removePlayer(Player player)
    {
        players.remove(player.getUniqueId());
    }

    public List<UUID> getPlayers()
    {
        return players;
    }

    public Location getSpawnOne()
    {
        return spawnOne;
    }

    public Location getSpawnTwo()
    {
        return spawnTwo;
    }

    public Location getLobby()
    {
        return lobby;
    }

    public UUID getPlayerOne()
    {
        return players.get(0);
    }

    public UUID getPlayerTwo()
    {
        return players.get(1);
    }

    /*
     * Arena Info
     */

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Game getGame()
    {
        return game;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }
}
