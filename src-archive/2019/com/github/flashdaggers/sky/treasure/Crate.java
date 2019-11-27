package com.github.fdx.sky.treasure;

import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.value.AValue;

/** @author FlashDaggerX */
public class Crate extends AValue {
    private List<Treasure> items;
    
    public Crate(Rarity worth) {
        super(worth);
        this.items = new ArrayList<>();
    }

    public void populate(List<Treasure> items) {
        this.items = items;
    }

    public void populate(Treasure[] items) {
        this.items = new ArrayList<>();
        for (Treasure t : items) this.items.add(t);
    }

    public List<Treasure> decide() {
        double seed = Math.random();
        for (Treasure t : items) {
            if (Math.ceil(seed*t.getRarity().rarity) == 0) {
                items.remove(t);
            }
        }

        return items;
    }
}
