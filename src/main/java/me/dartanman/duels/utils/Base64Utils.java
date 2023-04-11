package me.dartanman.duels.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Base64Utils
{

    public static String toBase64(ItemStack item)
    {
        try
        {
            return convertToBase64(item);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack fromBase64(String base64)
    {
        if(base64.isBlank() || base64.isEmpty())
        {
            return new ItemStack(Material.AIR);
        }
        try
        {
            return convertToItemStack(base64);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertToBase64(ItemStack item) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BukkitObjectOutputStream bOutput = new BukkitObjectOutputStream(output);
        bOutput.writeObject(item);
        return new String(Base64Coder.encode(output.toByteArray()));
    }

    private static ItemStack convertToItemStack(String base64) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream input = new ByteArrayInputStream(Base64Coder.decode(base64));
        BukkitObjectInputStream bInput = new BukkitObjectInputStream(input);
        return (ItemStack) bInput.readObject();
    }

}
