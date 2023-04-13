package me.dartanman.duels.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemSerializationUtils
{

    // Mostly works. Does not work with Lore or Attribute Modifiers.
    public static String serialize(ItemStack item)
    {
        String matStr = item.getType().toString();
        int amount = item.getAmount();

        String result = "{ material:" + matStr + " amount:" + amount + " meta:" +
                "{ %s } }";

        if(item.hasItemMeta())
        {
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            String metaStr = "";
            if(meta.hasDisplayName())
            {
                String displayName = meta.getDisplayName();
                metaStr = metaStr + "displayName:" + displayName;
            }
            if(meta.hasCustomModelData())
            {
                int cmd = meta.getCustomModelData();
                metaStr = metaStr + " customModelData:" + cmd;
            }
            if(meta.hasEnchants())
            {
                for(Enchantment ench : meta.getEnchants().keySet())
                {
                    String enchName = ench.getName();
                    int level = meta.getEnchantLevel(ench);
                    metaStr = metaStr + " " + enchName + ":" + level;
                }
            }
            if(meta.getItemFlags().size() > 0)
            {
                for(ItemFlag flag : meta.getItemFlags())
                {
                    String flagName = flag.name();
                    metaStr = metaStr + " " + flagName + ":true";
                }
            }
            if(meta.isUnbreakable())
            {
                metaStr = metaStr + " unbreakable:true";
            }

            metaStr = metaStr.strip();

            result = String.format(result, metaStr);
        }
        if(result.contains("%s"))
        {
            result = result.replace("%s", "");
        }
        return result;
    }

    public static ItemStack deserialize(String itemStr)
    {
        Material material = Material.STONE;
        int amount = 1;
        String[] stepOne = itemStr.split(" ");
        for(String str : stepOne)
        {
            str = str.toLowerCase();
            if(str.startsWith("material:"))
            {
                String[] mat = str.split(":");
                material = Material.valueOf(mat[1].toUpperCase());
            }
            else if (str.startsWith("amount:"))
            {
                String[] amt = str.split(":");
                amount = Integer.parseInt(amt[1]);
            }
        }
        if(itemStr.toLowerCase().contains("meta:") && !itemStr.toLowerCase().contains("meta:{ }"))
        {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta meta = item.getItemMeta();
            assert meta != null; // meta will never be null under CraftBukkit's current implementation (as of Apr. 12, 2023) but IntelliJ was screaming at me about it
            String metaStr = itemStr.toLowerCase().split("meta:")[1];
            String[] stepTwo = metaStr.split(" ");
            for(String str : stepTwo)
            {
                str = str.toLowerCase();
                if(str.startsWith("displayname:"))
                {
                    String[] name = str.split(":");
                    meta.setDisplayName(name[1]);
                }
                else if(str.startsWith("custommodeldata:"))
                {
                    String[] cmd = str.split(":");
                    meta.setCustomModelData(Integer.parseInt(cmd[1]));
                }
                else if(str.startsWith("unbreakable:"))
                {
                    String[] unb = str.split(":");
                    if(unb[1].equalsIgnoreCase("true"))
                    {
                        meta.setUnbreakable(true);
                    }
                }
                else if(str.contains(":"))
                {
                    String[] matcher = str.split(":");
                    Enchantment enchantment = Enchantment.getByName(matcher[0]);
                    if(enchantment != null)
                    {
                        int level = Integer.parseInt(matcher[1]);
                        meta.addEnchant(enchantment, level, true);
                    }
                    for(ItemFlag anyFlag : ItemFlag.values())
                    {
                        if(anyFlag.name().equalsIgnoreCase(matcher[0]) && str.split(":")[1].equalsIgnoreCase("true"))
                        {
                            meta.addItemFlags(anyFlag);
                        }
                    }
                }
            }
            item.setItemMeta(meta);
            return item;
        }
        else
        {
            return new ItemStack(material, amount);
        }
    }

}
