package com.github.fdx.sky.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;

/** @author FlashDaggerX */
public class Pool {
    private List<Treasure> treasures;

    public Pool() {
        this.treasures = new ArrayList<>();
    }

    public Pool add(Treasure treasure) {
        treasures.add(treasure);

        return this;
    }

    public Pool add(Material item, Rarity rarity, int quantity) {
        treasures.add(new Treasure(item, rarity, quantity));

        return this;
    }

    public Pool add(Material item, Rarity rarity) { 
        add(item, rarity, 1); 

        return this;
    }

    public Iterator<Treasure> getTreasures() {
        return treasures.iterator();
    }
}
