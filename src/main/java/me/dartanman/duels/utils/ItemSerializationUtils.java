package me.dartanman.duels.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;

import org.bukkit.inventory.ItemStack;

public class ItemSerializationUtils
{

    public static String serialize(ItemStack item) {
        ReadWriteNBT nbt = NBT.itemStackToNBT(item);
        return nbt.toString();
    }

    public static ItemStack deserialize(String itemStr) {
        ReadWriteNBT nbt = NBT.parseNBT(itemStr);
        return NBT.itemStackFromNBT(nbt);
    }

}
