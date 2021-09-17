package main.dartanman.duels.arenas;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.dartanman.duels.Main;
import main.dartanman.duels.utils.PlayerRestorationInfo;

import org.bukkit.ChatColor;


/**
 * Arena - Contains information for a duel and several methods pertaining to a duel
 * @author Austin Dart (Dartanman)
 */
public class Arena {
	
	private Main plugin;
	private int id;
	private String name;
	private Location spawn1;
	private Location spawn2;
	private boolean active = false;
	private Player[] players;
	
	/**
	 * Constructs a new Arena with the given ID and name
	 * @param id
	 *   The arena's ID
	 * @param name
	 *   The arena's name
	 */
	public Arena(int id, String name, Main pl) {
		this.id = id;
		this.name = name;
		plugin = pl;
		players = new Player[2];
	}
	
	/**
	 * Get Player 1
	 * @return
	 *   Player 1
	 */
	public Player getPlayer1() {
		return players[0];
	}
	
	/**
	 * Get Player 2
	 * @return
	 *   Player 2
	 */
	public Player getPlayer2() {
		return players[1];
	}
	
	/**
	 * Ends the game, specifies a winner and loser
	 * @param winner
	 *   The winner
	 * @param loser
	 *   The loser
	 */
	public void end(Player winner, Player loser) {
		winner.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.WinMessage")));
		plugin.getStatUtils().addWin(winner);
		PlayerRestorationInfo pri = new PlayerRestorationInfo(null);
		loser.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LoseMessage")));
		for(PlayerRestorationInfo priL : PlayerRestorationInfo.pris) {
			if(priL.getPlayer().getName().equals(winner.getName())) {
				pri = priL;
			}
		}
		pri.apply();
		PlayerRestorationInfo.pris.remove(pri);
		players[0] = null;
		players[1] = null;
		active = false;
	}
	
	/**
	 * Starts a duel if both spawns are set.
	 */
	public void start() {
		PlayerRestorationInfo pri1 = new PlayerRestorationInfo(players[0]);
		PlayerRestorationInfo pri2 = new PlayerRestorationInfo(players[1]);
		active = true;
		if(spawn1 == null || spawn2 == null) {
			for(Player p : players) {
				p.sendMessage(ChatColor.RED + "Unfortunately, this Arena has been set up incorrectly. Contact an Administrator to fix this!");
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(They forgot to set the spawn points)");
			}
			players[0] = null;
			players[1] = null;
			active = false;
			return;
		}
		for(Player p : players) {
			p.setGameMode(GameMode.ADVENTURE);
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.PrepareToDuelMessage")));
			if(plugin.getKitManager().getKitMap().containsKey(p.getUniqueId())) {
				plugin.getKitManager().giveKit(p, plugin.getKitManager().getKitMap().get(p.getUniqueId()));
			}else {
				plugin.getKitManager().giveKit(p, "default");
			}
		}
		players[0].teleport(spawn1);
		players[1].teleport(spawn2);
	}
	
	/**
	 * Checks if this arena contains the given player
	 * @param player
	 *   The player to check for
	 * @return
	 *   Whether or not the player is in this arena
	 */
	public boolean containsPlayer(Player player) {
		for(Player p : players) {
			try {
				if(p.getName().equals(player.getName())) {
					return true;
				}
			}catch(NullPointerException e) {
				// null is not the player
				continue;
			}
		}
		return false;
	}
	
	/**
	 * Adds a player to the arena. True if successful.
	 * @param player
	 *   The player to add
	 * @return
	 *   Whether or not the player was added
	 */
	public boolean addPlayer(Player player) {
		if(players[0] == null) {
			players[0] = player;
			return true;
		}else if(players[1] == null) {
			players[1] = player;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Removes a player from the arena. True if successful.
	 * @param player
	 *   The player to remove
	 * @return
	 *   Whether or not the player was removed
	 */
	public boolean removePlayer(Player player) {
		if(players[0].getName().equals(player.getName())) {
			players[0] = null;
			return true;
		}else if (players[1].getName().equals(player.getName())) {
			players[1] = null;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Returns whether or not the arena has 2 players (AKA, if it can start a duel)
	 * @return
	 *   Whether or not the duel can start
	 */
	public boolean canStart() {
		if(players[0] != null && players[1] != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Returns active status of the arena
	 * @return
	 *   Active status of the arena
	 */
	public boolean getActive() {
		return active;
	}
	
	/**
	 * Returns the arena's ID
	 * @return
	 *   The arena's ID
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the arena's name
	 * @return
	 *   The arena's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the first spawnpoint
	 * @return
	 *   The first spawnpoint
	 */
	public Location getSpawn1() {
		return spawn1;
	}
	
	/**
	 * Sets the first spawnpoint
	 * @param spawn1
	 *   A spawnpoint
	 */
	public void setSpawn1(Location spawn1) {
		this.spawn1 = spawn1;
	}
	
	/**
	 * Returns the second spawnpoint
	 * @return
	 *   The second spawnpoint
	 */
	public Location getSpawn2() {
		return spawn2;
	}
	
	/**
	 * Sets the second spawnpoint
	 * @param spawn2
	 *   A spawnpoint
	 */
	public void setSpawn2(Location spawn2) {
		this.spawn2 = spawn2;
	}

}
