package me.dartanman.duels.game.arenas;

import org.bukkit.Location;

/**
 * ArenaConfig -- The idea is to create all the information an Arena needs, then create it.
 * This is better that what I used to do because if you created only half an Arena, problems could occur.
 * This shouldn't happen now.
 *
 * @author Austin Dart (Dartanman)
 */
public class ArenaConfig
{

    private final int id;
    private final String name;

    private Location spawnOne;
    private Location spawnTwo;
    private Location lobby;

    public ArenaConfig(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public boolean isFinished()
    {
        return spawnOne != null && spawnTwo != null && lobby != null;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Location getSpawnOne()
    {
        return spawnOne;
    }

    public void setSpawnOne(Location spawnOne)
    {
        this.spawnOne = spawnOne;
    }

    public Location getSpawnTwo()
    {
        return spawnTwo;
    }

    public void setSpawnTwo(Location spawnTwo)
    {
        this.spawnTwo = spawnTwo;
    }

    public Location getLobby()
    {
        return lobby;
    }

    public void setLobby(Location lobby)
    {
        this.lobby = lobby;
    }
}
