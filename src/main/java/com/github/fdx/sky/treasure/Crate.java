package com.github.fdx.sky.treasure;

import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.value.AValue;
import com.github.fdx.sky.value.ISeedable;

/** @author FlashDaggerX */
public class Crate extends AValue implements ISeedable {
    private List<Treasure> items;
    
    public Crate(Rarity worth) {
        super(worth);
        this.items = new ArrayList<>();
    }

    public void populate(List<Treasure> items) {
        this.items = items;
    }

    public List<Treasure> decide() {
        double seed = rand().nextDouble();
        for (Treasure t : items) {
            if (Math.ceil(seed*t.getRarity().rarity) == 0) {
                items.remove(t);
            }
        }

        return items;
    }
}
