package com.github.fdx.sky.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;

/** @author FlashDaggerX */
public class Crate {
    private List<Material> materials;
    private Pool pool;

    public Crate(Pool p) {
        this.materials = new ArrayList<>();
        this.pool = p;
    }

    /** Decides the items from the pool, at random. */
    public void randomize() {
        Random rand = new Random();
        double seed = rand.nextDouble();
        double chance = 0.0;

        for (Treasure t : pool.pool()) {
            // TODO: Figure out the chance algorithm. Reference chance.py in "test"
        }
    }
}
