package me.dartanman.duels.game.kits;

import me.dartanman.duels.Duels;
import me.dartanman.duels.game.arenas.Arena;
import me.dartanman.duels.utils.Base64Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KitManager
{

    private final Duels plugin;
    private final List<Kit> kitList;

    public KitManager(Duels plugin)
    {
        this.plugin = plugin;
        this.kitList = new ArrayList<>();
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
            armor[3] = Base64Utils.fromBase64(helmetBase64);
            armor[2] = Base64Utils.fromBase64(chestplateBase64);
            armor[1] = Base64Utils.fromBase64(leggingsBase64);
            armor[0] = Base64Utils.fromBase64(bootsBase64);

            ItemStack[] inventory = new ItemStack[inventoryBase64.size()];
            int i = 0;
            for(String base64 : inventoryBase64)
            {
                inventory[i++] = Base64Utils.fromBase64(base64);
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

        ItemStack[] armor = {boots, leggings, chestplate, helmet};
        ItemStack[] inventory = player.getInventory().getContents();

        plugin.getConfig().set("Kits." + id + ".Name", kitName);
        plugin.getConfig().set("Kits." + id + ".Armor.Helmet", Base64Utils.toBase64(helmet));
        plugin.getConfig().set("Kits." + id + ".Armor.Chestplate", Base64Utils.toBase64(chestplate));
        plugin.getConfig().set("Kits." + id + ".Armor.Leggings", Base64Utils.toBase64(leggings));
        plugin.getConfig().set("Kits." + id + ".Armor.Boots", Base64Utils.toBase64(boots));
        List<String> invList = new ArrayList<>();
        for(ItemStack item : inventory)
        {
            if(item != null)
            {
                invList.add(Base64Utils.toBase64(item));
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
