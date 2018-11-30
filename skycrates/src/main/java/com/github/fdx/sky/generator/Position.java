package com.github.fdx.sky.generator;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;

/** @author FlashDaggerX */
public class Position {
    enum Cord { X, Z, Y };

    private World world;
    private int[] bounds;

    public Position(int[] bounds, World world) {
        this.bounds = bounds;
        this.world = world;
    }

    public Position(int[] bounds) {
        // Get the First world.
        this(bounds, Bukkit.getServer().getWorlds().get(0));
    }

    public int[] getPos() {
        Random rand = new Random();
        int[] coord = new int[3];

        int x = Cord.X.ordinal(), z = Cord.Z.ordinal(), y = Cord.Y.ordinal();

        coord[x] = rand.nextInt(bounds[x]); 
        coord[z] = rand.nextInt(bounds[z]);
        coord[y] = world.getHighestBlockYAt(coord[x], coord[z]);

        return coord;
    }
}
