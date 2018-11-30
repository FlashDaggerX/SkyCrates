package com.github.fdx.sky.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.github.fdx.sky.pool.Pool;
import com.github.fdx.sky.pool.Treasure;

public class Items {
    /** The chance an item must pass to be spawned. */
    public static final int LIMITER = 24;

    private Pool pool;

    public Items(Pool pool) {
        this.pool = pool;
    }

    public List<Treasure> decideItems() {
        List<Treasure> items = new ArrayList<>();

        Iterator<Treasure> iter = pool.getTreasures().iterator();
        double seed = new Random().nextDouble();
        Treasure treasure;
        while (iter.hasNext()) {
            treasure = iter.next();

            double chance = seed * treasure.worth.rarity;
            if (chance > Items.LIMITER) {
                items.add(treasure);
            }

            iter.remove();
        }

        return items;
    }
}
