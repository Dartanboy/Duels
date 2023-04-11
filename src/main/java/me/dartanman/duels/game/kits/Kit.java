package me.dartanman.duels.game.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit
{
    private final int id;
    private final String name;

    private final ItemStack[] armor;
    private final ItemStack[] inventory;

    public Kit(int id, String name, ItemStack[] armor, ItemStack[] inventory)
    {
        this.id = id;
        this.name = name;
        this.armor = armor;
        this.inventory = inventory;
    }

    public void apply(Player player)
    {
        player.getInventory().clear();
        player.getInventory().setArmorContents(armor);
        player.getInventory().addItem(inventory);
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
