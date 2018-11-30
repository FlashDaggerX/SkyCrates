package com.github.fdx.sky.pool;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/** @author FlashDaggerX */
public class Crate {
    private Pool pool;
    private Rarity worth;
    private Inventory inv;

    private Treasure crate;

    /**
     * Creates an item crate.
     * @param pool The item pool.
     * @param worth The crates' chance value.
     */
    public Crate(Pool pool, Rarity worth) {
        this.pool = pool;
        this.worth = worth;
        this.crate = new Treasure(Material.CHEST, worth);
    }

    /**
     * Creates an item crate.
     * @param pool The item pool.
     */
    public Crate(Pool pool) {
        this(pool, Rarity.NORMAL);
    }

    public Pool getPool() {
        return pool;
    }

    /**
     * 
     * @return The crate's inventory.
     */
    public Inventory getChestInventory() {
        if (inv == null) {
            inv = Bukkit.createInventory(null, InventoryType.ENDER_CHEST);
        }

        return inv;
    }
}
