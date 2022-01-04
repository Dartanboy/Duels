package main.dartanman.duels.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import main.dartanman.duels.Main;

/**
 * LeaderboardUtils - Utility class for showing a Wins leaderboard
 * @author Austin Dart (Dartanman)
 */
public class LeaderboardUtils {
	
	private Main plugin;
	
	public LeaderboardUtils(Main pl) {
		plugin = pl;
	}
    
    public List<String> createLeaderboard(){
    	List<String> unsorted = new ArrayList();
    	List<String> sorted = new ArrayList();
    	Set<String> uuids = plugin.getStatsFile().getConfigurationSection("Wins").getKeys(false);
    	for(String uuidStr : uuids) {
    		int wins = plugin.getStatsFile().getInt("Wins." + uuidStr);
    		OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuidStr));
    		unsorted.add(op.getName() + "/" + wins);
    	}
    	for(int i = 0; i < 10; i++) {
    		if(unsorted.size() == 0) {
    			break;
    		}
    		String highest = unsorted.get(0);
    		for(int j = 0; j < unsorted.size(); j++) {
    			String[] oldSplit = highest.split("/");
    			int oldWins = Integer.parseInt(oldSplit[1]);
    			String[] newSplit = unsorted.get(j).split("/");
    			int newWins = Integer.parseInt(newSplit[1]);
    			if(newWins >= oldWins) {
    				highest = unsorted.get(j);
    			}
    		}
    		sorted.add(highest.replace("/", ": "));
    		unsorted.remove(highest);
    	}
    	return sorted;
    }

}
