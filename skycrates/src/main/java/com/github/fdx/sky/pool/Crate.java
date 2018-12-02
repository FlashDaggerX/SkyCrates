package com.github.fdx.sky.pool;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/** @author FlashDaggerX */
public class Crate {
    /** The ID of the crate in the scheduler. */
    public int id;
    public Rarity worth;

    private Pool pool;
    private Inventory inv;

    /**
     * Creates an item crate.
     * @param pool The item pool.
     * @param worth The crates' chance value.
     */
    public Crate(int id, Pool pool, Rarity worth) {
        this.id = id;
        this.pool = pool;
        this.worth = worth;
    }

    /**
     * Creates an item crate.
     * @param pool The item pool.
     */
    public Crate(int id, Pool pool) {
        this(id, pool, Rarity.NORMAL);
    }

    /**
     * 
     * @return The crates' selected item pool.
     */
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
