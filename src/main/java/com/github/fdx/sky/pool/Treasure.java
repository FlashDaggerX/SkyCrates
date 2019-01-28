package com.github.fdx.sky.pool;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/** @author FlashDaggerX */
public class Treasure {
    public Material material;

    public Rarity worth;
    public int quantity, durability;

    public Treasure(Material material, Rarity worth, int quantity, int durability) {
        this.material = material;
        this.worth = worth;
        this.quantity = quantity;
        this.durability = durability;
    }
    
    public Treasure(Material material, Rarity worth) {
        this(material, worth, 1, 100);
    }

    public ItemStack getItem() {
        if (durability > Short.MAX_VALUE || durability < Short.MIN_VALUE) {
            throw new NumberFormatException("Durability isn't an Integer!");
        }

        ItemStack item = new ItemStack(material, quantity);
        item.setDurability((short) durability);
        return item;
    }

    public ItemStack getItem(ItemMeta itemMeta) {
        ItemStack item = getItem();
        item.setItemMeta(itemMeta);

        return item;
    }
}
