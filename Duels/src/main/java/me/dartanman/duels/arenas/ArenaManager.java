package me.dartanman.duels.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.dartanman.duels.Main;
import net.md_5.bungee.api.ChatColor;

/**
 * ArenaManager - Manages Arenas (pretty self-explanatory name)
 * @author Austin Dart (Dartanman)
 */

public class ArenaManager {
	
	private Main plugin;
	
	private int nextID = 0;
	
	private List<Arena> arenaList = new ArrayList();
	
	/**
	 * Constructs the ArenaManager with access to the Main class
	 * @param pl
	 */
	public ArenaManager(Main pl) {
		plugin = pl;
		try {
			loadAllArenasFromFile();	
		}catch(Exception e) {
			e.printStackTrace();
			plugin.getLogger().info("Failed to load any arenas. Perhaps you haven't made one yet?");
			plugin.getLogger().info(ChatColor.GREEN + "If this is the first time you've loaded this plugin, the above error is normal. Don't worry about it.");
		}
	}
	
	/**
	 * Returns a list of all arenas
	 * @return
	 */
	public List<Arena> getArenaList(){
		return arenaList;
	}
	
	/**
	 * Loads all arenas from config.yml
	 */
	public void loadAllArenasFromFile() {
		FileConfiguration c = plugin.getConfig();
		for(String arenaName : c.getConfigurationSection("Arenas").getKeys(false)) {
			Arena arena = createArena(arenaName);
			String s1Str = c.getString("Arenas." + arenaName + ".Spawn1");
			String[] s1Splice = s1Str.split("/");
			World s1World = Bukkit.getWorld(s1Splice[0]);
			double s1X = Double.parseDouble(s1Splice[1]);
			double s1Y = Double.parseDouble(s1Splice[2]);
			double s1Z = Double.parseDouble(s1Splice[3]);
			float s1Yaw = Float.parseFloat(s1Splice[4]);
			float s1Pitch = Float.parseFloat(s1Splice[5]);
			Location spawn1 = new Location(s1World, s1X, s1Y, s1Z, s1Yaw, s1Pitch);
			arena.setSpawn1(spawn1);
			String s2Str = c.getString("Arenas." + arenaName + ".Spawn2");
			String[] s2Splice = s2Str.split("/");
			World s2World = Bukkit.getWorld(s2Splice[0]);
			double s2X = Double.parseDouble(s2Splice[1]);
			double s2Y = Double.parseDouble(s2Splice[2]);
			double s2Z = Double.parseDouble(s2Splice[3]);
			float s2Yaw = Float.parseFloat(s2Splice[4]);
			float s2Pitch = Float.parseFloat(s2Splice[5]);
			Location spawn2 = new Location(s2World, s2X, s2Y, s2Z, s2Yaw, s2Pitch);
			arena.setSpawn2(spawn2);
			saveArenaToList(arena);
		}
	}
	
	/**
	 * Saves an arena to config.yml
	 * @param arena
	 *   The arena to save
	 */
	public void saveArenaToFile(Arena arena) {
		FileConfiguration c = plugin.getConfig();
		String path = "Arenas." + arena.getName();
		Location s1 = arena.getSpawn1();
		Location s2 = arena.getSpawn2();
		if(s1 == null || s2 == null) {
			return;
		}
		World s1World = s1.getWorld();
		double s1X = s1.getX();
		double s1Y = s1.getY();
		double s1Z = s1.getZ();
		float s1Yaw = s1.getYaw();
		float s1Pitch = s1.getPitch();
		c.set(path + ".Spawn1", s1World.getName() + "/" + s1X + "/" + s1Y + "/" + s1Z + "/" + s1Yaw + "/" + s1Pitch);
		World s2World = s2.getWorld();
		double s2X = s2.getX();
		double s2Y = s2.getY();
		double s2Z = s2.getZ();
		float s2Yaw = s2.getYaw();
		float s2Pitch = s2.getPitch();
		c.set(path + ".Spawn2", s2World.getName() + "/" + s2X + "/" + s2Y + "/" + s2Z + "/" + s2Yaw + "/" + s2Pitch);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	/**
	 * Adds an Arena to arenaList
	 * @param arena
	 *   The arena to add
	 */
	public void saveArenaToList(Arena arena) {
		arenaList.add(arena);
	}
	
	/**
	 * Creates an arena with the given name
	 * @param name
	 *   The arena's name
	 * @return
	 */
	public Arena createArena(String name) {
		Arena arena = createArena(nextID, name);
		nextID++;
		return arena;
	}
	
	/**
	 * Creates an arena with the given ID and name
	 * @param id
	 *   The arena's ID
	 * @param name
	 *   The arena's name
	 * @return
	 */
	private Arena createArena(int id, String name) {
		Arena arena = new Arena(id, name, plugin);
		return arena;
	}
	
	/**
	 * Finds an Arena by the given name
	 * @param name
	 *   The arena's name
	 * @return
	 *   The arena if found, or null if not
	 */
	public Arena getArena(String name) {
		for(Arena arena : arenaList) {
			if (arena.getName().equalsIgnoreCase(name)) {
				return arena;
			}
		}
		return null;
	}
	
	/**
	 * Finds an Arena by one of its players.
	 * @param player
	 *   The player
	 * @return
	 *   The arena if found, or null if not
	 */
	public Arena getArena(Player player) {
		for(Arena arena : arenaList) {
			if(arena.containsPlayer(player)) {
				return arena;
			}
		}
		return null;
	}

}
