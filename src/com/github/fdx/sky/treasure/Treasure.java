package com.github.fdx.sky.treasure;

import com.github.fdx.sky.value.AValue;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/** @author FlashDaggerX */
public class Treasure extends AValue {
    // Treasure doesn't implement "Seedable," since item generation
    // is performed during crate selection.
    public Material material;

    public int quantity, durability;

    public Treasure(Material material, Rarity worth, int quantity, int durability) {
        super(worth);
        this.material = material;
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
        if (durability != 0) item.setDurability((short) durability);
        return item;
    }

    public ItemStack getItem(ItemMeta itemMeta) {
        ItemStack item = getItem();
        item.setItemMeta(itemMeta);

        return item;
    }
}
