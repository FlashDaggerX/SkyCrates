package com.github.fdx.sky.pool;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/** @author FlashDaggerX */
public class Treasure {
    public Material material;

    public Rarity worth;
    public int quantity;
    // TODO: Possibly add quality?

    public Treasure(Material material, Rarity worth, int quantity) {
        this.material = material;
        this.worth = worth;
        this.quantity = quantity;
    }
    
    public Treasure(Material material, Rarity worth) {
        this(material, worth, 1);
    }

    public ItemStack getItem() {
        return new ItemStack(material, quantity);
    }

    public ItemStack getSpecialItem(ItemMeta itemMeta) {
        ItemStack item = getItem();
        item.setItemMeta(itemMeta);

        return item;
    }
}
