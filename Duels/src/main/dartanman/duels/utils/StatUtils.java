package main.dartanman.duels.utils;

import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import main.dartanman.duels.Main;

/**
 * StatUtils - Utility class to edit stuff in Stats.yml
 * @author Austin Dart (Dartanman)
 */
public class StatUtils {
	
	private Main plugin;
	
	/**
	 * Constructs StatUtils
	 * @param pl
	 *   Main class
	 */
	public StatUtils(Main pl) {
		plugin = pl;
	}
	
	/**
	 * Adds 1 win to a player
	 * @param player
	 *   The player to add a win to
	 */
	public void addWin(Player player) {
		UUID uuid = player.getUniqueId();
		String uuidStr = uuid.toString();
		plugin.getStatsFile().set("Wins." + uuidStr, getWins(player) + 1);
		plugin.saveStatsFile();
	}
	
	/**
	 * Gets the amount of wins a given player has
	 * @param player
	 *   The player to check
	 * @return
	 *   Their amount of wins
	 */
	public int getWins(Player player) {
		UUID uuid = player.getUniqueId();
		String uuidStr = uuid.toString();
		Set<String> players = plugin.getStatsFile().getConfigurationSection("Wins").getKeys(false);
		if(players.contains(uuidStr)) {
			return plugin.getStatsFile().getInt("Wins." + uuidStr);
		}else {
			return 0;
		}
	}

}
