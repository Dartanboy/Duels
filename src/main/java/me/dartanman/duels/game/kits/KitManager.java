package me.dartanman.duels.game.kits;

import me.dartanman.duels.Duels;
import me.dartanman.duels.utils.ItemSerializationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class KitManager
{

    public static final HashMap<UUID, String> kitMap = new HashMap<>();

    public static void selectKit(Player player, String kit)
    {
        kitMap.put(player.getUniqueId(), kit);
    }

    private final Duels plugin;
    private final List<Kit> kitList;

    public KitManager(Duels plugin)
    {
        this.plugin = plugin;
        this.kitList = new ArrayList<>();

        loadKits();
    }

    public void giveKit(Player player)
    {
        Kit kit = getKit("Default");
        if(kitMap.containsKey(player.getUniqueId()))
        {
            kit = getKit(kitMap.get(player.getUniqueId()));
            if(kit == null)
            {
                kit = getKit("Default");
            }
        }
        kit.apply(player);
    }

    private void loadKits()
    {
        FileConfiguration config = plugin.getConfig();
        if(!config.contains("Kits"))
        {
            return;
        }
        for(String kitIdStr : Objects.requireNonNull(config.getConfigurationSection("Kits")).getKeys(false))
        {
            String path = "Kits." + kitIdStr;
            int id;
            try
            {
                id = Integer.parseInt(kitIdStr);
            }
            catch (NumberFormatException e)
            {
                Bukkit.getLogger().severe("Failed to parse integer '" + kitIdStr + "' from config.yml (kit id)");
                continue;
            }

            String name = plugin.getConfig().getString(path + ".Name");
            String helmetBase64 = plugin.getConfig().getString(path + ".Armor.Helmet");
            String chestplateBase64 = plugin.getConfig().getString(path + ".Armor.Chestplate");
            String leggingsBase64 = plugin.getConfig().getString(path + ".Armor.Leggings");
            String bootsBase64 = plugin.getConfig().getString(path + ".Armor.Boots");
            List<String> inventoryBase64 = plugin.getConfig().getStringList(path + ".Inventory");

            assert helmetBase64 != null;
            assert chestplateBase64 != null;
            assert leggingsBase64 != null;
            assert bootsBase64 != null;

            ItemStack[] armor = new ItemStack[4];
            armor[3] = ItemSerializationUtils.deserialize(helmetBase64);
            armor[2] = ItemSerializationUtils.deserialize(chestplateBase64);
            armor[1] = ItemSerializationUtils.deserialize(leggingsBase64);
            armor[0] = ItemSerializationUtils.deserialize(bootsBase64);

            ItemStack[] inventory = new ItemStack[inventoryBase64.size()];
            int i = 0;
            for(String base64 : inventoryBase64)
            {
                inventory[i++] = ItemSerializationUtils.deserialize(base64);
            }

            kitList.add(new Kit(id, name, armor, inventory));
        }
    }

    public void deleteKit(String kitName)
    {
        Kit kit = getKit(kitName);
        if(kit != null)
        {
            plugin.getConfig().set("Kits." + kit.getId(), null);
            plugin.saveConfig();
            kitList.remove(kit);
        }
    }

    public Kit createKit(String kitName, Player player)
    {
        int id = getNextId();
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        if(helmet == null)
        {
            helmet = new ItemStack(Material.AIR);
        }
        if(chestplate == null)
        {
            chestplate = new ItemStack(Material.AIR);
        }
        if(leggings == null)
        {
            leggings = new ItemStack(Material.AIR);
        }
        if(boots == null)
        {
            boots = new ItemStack(Material.AIR);
        }

        ItemStack[] armor = {boots, leggings, chestplate, helmet};
        ItemStack[] inventory = player.getInventory().getContents();

        // ignore armor
        inventory[inventory.length - 2] = null;
        inventory[inventory.length - 3] = null;
        inventory[inventory.length - 4] = null;
        inventory[inventory.length - 5] = null;

        plugin.getConfig().set("Kits." + id + ".Name", kitName);
        plugin.getConfig().set("Kits." + id + ".Armor.Helmet", ItemSerializationUtils.serialize(helmet));
        plugin.getConfig().set("Kits." + id + ".Armor.Chestplate", ItemSerializationUtils.serialize(chestplate));
        plugin.getConfig().set("Kits." + id + ".Armor.Leggings", ItemSerializationUtils.serialize(leggings));
        plugin.getConfig().set("Kits." + id + ".Armor.Boots", ItemSerializationUtils.serialize(boots));
        List<String> invList = new ArrayList<>();
        for(ItemStack item : inventory)
        {
            if(item != null)
            {
                invList.add(ItemSerializationUtils.serialize(item));
            }
        }

        plugin.getConfig().set("Kits." + id + ".Inventory", invList);
        plugin.saveConfig();

        Kit kit = new Kit(id, kitName, armor, inventory);
        kitList.add(kit);
        return kit;
    }

    public Kit getKit(String kitName)
    {
        for(Kit kit : kitList)
        {
            if(kit.getName().equalsIgnoreCase(kitName))
            {
                return kit;
            }
        }
        return null;
    }

    public List<Kit> getKitList()
    {
        return kitList;
    }

    public int getNextId()
    {
        int max = 0;
        for(Kit kit : kitList)
        {
            int id = kit.getId();
            if(id > max)
            {
                max = id;
            }
        }
        return max + 1;
    }

}
