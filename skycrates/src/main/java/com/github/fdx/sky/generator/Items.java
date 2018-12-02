package com.github.fdx.sky.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.github.fdx.sky.pool.Pool;
import com.github.fdx.sky.pool.Treasure;

import org.bukkit.inventory.ItemStack;

public class Items {
    /** The chance an item must pass to be spawned. */
    public static final int LIMITER = 24;
    
    private Pool pool;

    public Items(Pool pool) {
        this.pool = pool;
    }

    public ItemStack[] decideItems() {
        List<Treasure> items = new ArrayList<>();

        Iterator<Treasure> iter = pool.getTreasures();
        double seed = new Random().nextDouble();
        
        while (iter.hasNext()) {
            Treasure treasure = iter.next();

            double chance = (seed * treasure.worth.rarity) * 100;
            if (chance > Items.LIMITER) {
                items.add(treasure);
            }

            iter.remove();
        }

        ItemStack[] inventoryContent = new ItemStack[items.size()];
        for (int i = 0; i < items.size(); i++) {
            inventoryContent[i] = items.get(i).getItem();
        }

        return inventoryContent;
    }
}
