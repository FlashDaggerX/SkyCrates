package com.github.fdx.sky.generator;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/** @author FlashDaggerX */
public class Position {
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

    public Location getPos() {
        Random rand = new Random();
        int[] coord = new int[3];

        int x = 0, z = 1, y = 2;

        coord[x] = rand.nextInt(bounds[x]); 
        coord[z] = rand.nextInt(bounds[z]);
        coord[y] = world.getHighestBlockYAt(coord[x], coord[z]);

        return new Location(getWorld(), coord[x], coord[y], coord[z]);
    }

    public World getWorld() {
        return world;
    }
}
