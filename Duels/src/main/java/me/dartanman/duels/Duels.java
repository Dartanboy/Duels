package me.dartanman.duels;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import me.dartanman.duels.arenas.ArenaManager;
import me.dartanman.duels.commands.DuelCmd;
import me.dartanman.duels.commands.DuelTabCompleter;
import me.dartanman.duels.events.ArenaEvents;
import me.dartanman.duels.kits.KitManager;
import me.dartanman.duels.utils.LeaderboardUtils;
import me.dartanman.duels.utils.StatUtils;

/**
 * Main - The Main class of Duels
 * @author Austin Dart (Dartanman)
 */
public class Duels extends JavaPlugin{
	
	private FileConfiguration statsFile = new YamlConfiguration();
	private File statsF;
	
	private ArenaManager arenaManager;
	private KitManager kitManager;
	private StatUtils statUtils;
	private LeaderboardUtils lbUtils;
	
	/**
	 * Enables the plugin
	 */
	public void onEnable() {
		int pluginId = 12801; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		createFiles();
		arenaManager = new ArenaManager(this);
		kitManager = new KitManager(this);
		statUtils = new StatUtils(this);
		lbUtils = new LeaderboardUtils(this);
		getCommand("duels").setExecutor(new DuelCmd(this));
		getCommand("duels").setTabCompleter(new DuelTabCompleter());
		getServer().getPluginManager().registerEvents(new ArenaEvents(this), this);
	}
	
	/**
	 * Get the stats file
	 * @return
	 *   The stats file
	 */
	public FileConfiguration getStatsFile() {
		return statsFile;
	}
	
	/**
	 * Save the stats file
	 */
	public void saveStatsFile() {
		try {
			statsFile.save(statsF);
		}catch(IOException e) {
			e.printStackTrace();
			getLogger().warning("Failed to save Stats.yml");
		}
	}
	
	/**
	 * Create necessary files. Right now, just Stats.yml
	 */
	private void createFiles() {
		statsF = new File(getDataFolder(), "Stats.yml");
		saveRes(statsF, "Stats.yml");
		statsFile = new YamlConfiguration();
		try {
			statsFile.load(statsF);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save resource with a few more steps
	 * @param file
	 *   File to save
	 * @param name
	 *   Name to save it as
	 */
	private void saveRes(File file, String name) {
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			saveResource(name, false);
		}
	}
	
	/**
	 * Returns the LeaderboardUtils
	 * @return
	 *   LeaderboardUtils
	 */
	public LeaderboardUtils getLBUtils() {
		return lbUtils;
	}
	
	/**
	 * Returns the StatUtils
	 * @return
	 *   The StatUtils
	 */
	public StatUtils getStatUtils() {
		return statUtils;
	}
	
	/**
	 * Returns the ArenaManager
	 * @return
	 *   The ArenaManager
	 */
	public ArenaManager getArenaManager() {
		return arenaManager;
	}
	
	/**
	 * Returns the KitManager
	 * @return
	 *   The KitManager
	 */
	public KitManager getKitManager() {
		return kitManager;
	}
	
	/**
     * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is the armor.
     * 
     * @param playerInventory to turn into an array of strings.
     * @return Array of strings: [ main content, armor content ]
     * @throws IllegalStateException
     */
    public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
    	Inventory inv = Bukkit.createInventory(null, 36);
    	ItemStack[] items = playerInventory.getContents();
    	ItemStack[] onlyMainItems = new ItemStack[36];
    	for(int i = 0; i < onlyMainItems.length; i++) {
    		onlyMainItems[i] = items[i];
    	}
    	inv.setContents(onlyMainItems);
    	String content = toBase64(inv);
    	String armor = itemStackArrayToBase64(playerInventory.getArmorContents());
    	
    	return new String[] { content, armor };
    }
    
    /**
     * 
     * A method to serialize an {@link ItemStack} array to Base64 String.
     * 
     * <p />
     * 
     * Based off of {@link #toBase64(Inventory)}.
     * 
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
    	try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            
            // Write the size of the inventory
            dataOutput.writeInt(items.length);
            
            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }
    
    /**
     * A method to serialize an inventory to Base64 string.
     * 
     * <p />
     * 
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     * 
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     * 
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     * @throws IllegalStateException
     */
    public static String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            
            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
            
            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }
    
    /**
     * 
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     * 
     * <p />
     * 
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     * 
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     * 
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     * @throws IOException
     */
    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
    
            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
    
    /**
     * Gets an array of ItemStacks from Base64 string.
     * 
     * <p />
     * 
     * Base off of {@link #fromBase64(String)}.
     * 
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
    
            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
            	items[i] = (ItemStack) dataInput.readObject();
            }
            
            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

}
