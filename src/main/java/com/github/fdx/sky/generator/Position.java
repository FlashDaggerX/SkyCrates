package com.github.fdx.sky.generator;

import java.util.Random;

import com.github.fdx.sky.value.ISeedable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/** @author FlashDaggerX */
class Position implements ISeedable {
    private World world;
    private int[] bounds; // [2]

    public Position(int[] bounds, World world) {
        this.bounds = bounds;
        this.world = world;
    }

    public Position(int[] bounds) {
        // Get the First world, if no others are specified
        this(bounds, Bukkit.getServer().getWorlds().get(0));
    }

    public Location getPos() {
        Random rand = rand();
        int[] coord = new int[3]; // [3]

        coord[0] = rand.nextInt(bounds[0]);
        coord[1] = rand.nextInt(bounds[1]);
        coord[2] = world.getHighestBlockYAt(coord[0], coord[1]);

        return new Location(getWorld(), coord[0], coord[2], coord[1]);
    }

    public World getWorld() {
        return world;
    }
}
