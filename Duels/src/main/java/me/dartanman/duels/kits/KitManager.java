package me.dartanman.duels.kits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.dartanman.duels.Main;

/**
 * KitManager - Manages everything to do with kits
 * @author Austin Dart (Dartanman)
 */
public class KitManager {
	
	private Main plugin;
	
	/**
	 * Constructs a KitManager with access to the Main class
	 * @param pl
	 */
	public KitManager(Main pl) {
		plugin = pl;
	}
	
	private HashMap<UUID, String> kitMap = new HashMap();
	
	/**
	 * Regardless of capitalization, returns the actual name in the config file.
	 * @param kitName
	 *   The name of the kit, ignoring capitalization
	 * @return
	 *   The name of the kit, as it is in the config file
	 */
	private String commonToInternal(String kitName) {
		Set<String> kits = plugin.getConfig().getConfigurationSection("Kits").getKeys(false);
		for(String kit : kits) {
			if(kit.equalsIgnoreCase(kitName)) {
				return kit;
			}
		}
		return kitName;
	}
	
	/**
	 * HashMap of UUIDs to the equipped kit
	 * @return
	 *   The name of the equipped kit
	 */
	public HashMap<UUID, String> getKitMap(){
		return kitMap;
	}
	
	/**
	 * List all kits
	 * @return
	 *   List of kits
	 */
	public List<String> getKitNames(){
		List<String> kitList = new ArrayList();
		Set<String> kits = plugin.getConfig().getConfigurationSection("Kits").getKeys(false);
		for(String kit : kits) {
			kitList.add(kit);
		}
		return kitList;
	}
	
	/**
	 * Deletes a kit from the config file.
	 * @param kitName
	 *   The kit to delete
	 * @return
	 *   True if successful, false if not
	 */
	public boolean deleteKit(String kitName) {
		if(kitExists(kitName)) {
			plugin.getConfig().set("Kits." + kitName, null);
			plugin.saveConfig();
			plugin.reloadConfig();
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Checks if a kit exists or not
	 * @param kitName
	 *   Kit to check for
	 * @return
	 *   Whether or not kitName exists
	 */
	public boolean kitExists(String kitName) {
		Set<String> kits = plugin.getConfig().getConfigurationSection("Kits").getKeys(false);
		for(String kit : kits) {
			if(kit.equalsIgnoreCase(kitName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Save a kit by taking their inventory, converting to base64, and putting it in the config.yml
	 * @param player
	 *   Player to save the inventory of
	 * @param kitName
	 *   Name of the newly created kit
	 */
	public void saveKit(Player player, String kitName) {
		String[] kitToBase64 = Main.playerInventoryToBase64(player.getInventory());
		plugin.getConfig().set("Kits." + kitName, kitToBase64);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	/**
	 * Equip a kit by putting it in the kitMap
	 * @param player
	 *   The player to equip to
	 * @param kitName
	 *   The kit to equip
	 * @return
	 *   True if successful, false if not.
	 */
	public boolean setKit(Player player, String kitName) {
		if(kitExists(kitName)) {
			kitMap.put(player.getUniqueId(), kitName);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Give a kit to a player
	 * @param player
	 *   The player to give the kit to
	 * @param kitName
	 *   The kit to give
	 * @return
	 *   True if successful, false if not
	 */
	public boolean giveKit(Player player, String kitName) {
		if(!kitExists(kitName)) {
			kitName = "default";
		}
		kitName = commonToInternal(kitName);
		String contents = plugin.getConfig().getStringList("Kits." + kitName).get(0);
		String armor = plugin.getConfig().getStringList("Kits." + kitName).get(1);
		try {
			Inventory inv = Main.fromBase64(contents);
			ItemStack[] armors = Main.itemStackArrayFromBase64(armor);
			player.getInventory().setContents(inv.getContents());
			player.getInventory().setArmorContents(armors);
			player.updateInventory();
			return true;
		} catch (IOException e) {
			plugin.getLogger().warning("WARNING: Duels failed to give " + player.getName() + " their kit (" + kitName + ")! Please ensure it is not corrupted!");
			e.printStackTrace();
			return false;
		}
	}

}
