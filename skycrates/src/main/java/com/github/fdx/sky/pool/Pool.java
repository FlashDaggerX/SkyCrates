package com.github.fdx.sky.pool;

import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Material;

/** @author FlashDaggerX */
public class Pool {
    private List<Treasure> treasures;

    public Pool() {}

    public void add(Treasure treasure) {
        treasures.add(treasure);
    }

    public void add(Material item, Rarity rarity, int quantity) {
        treasures.add(new Treasure(item, rarity, quantity));
    }

    public void add(Material item, Rarity rarity) { 
        add(item, rarity, 1); 
    }

    public void remove(int index) {
        treasures.remove(index);
    }

    public void remove(Material item) {
        int index = 0;
        for (Treasure treasure : treasures) {
            if (treasure == null) {
                index++;
                continue;
            }

            if (treasure.material == item) {
                remove(index);
                break;
            }

            index++;
        }
    }

    public Stream<Treasure> getTreasures() {
        return treasures.stream();
    }
}
