package com.github.fdx.sky.generator;

import java.util.HashMap;

import com.github.fdx.sky.event.CrateSpawnEvent;
import com.github.fdx.sky.treasure.Crate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.scheduler.BukkitRunnable;

/** @author FlashDaggerX */
public class DeliveryQueue extends BukkitRunnable {
	/** The period counted by the scheduler. */
    public static long PERIOD = 1000L;
    /** The chance limiter. */
    public static final int LIMITER = 53;

    private static HashMap<Integer, Crate> QUEUE = new HashMap<>();

    /** Stores away a crate to be spawned on top of the stack */
    public static void push(Crate crate) { 
        QUEUE.put(QUEUE.size(), crate);
    }

    /** Removes the crate on top of the stack and returns it. */
    public static Crate pop() {
        return QUEUE.remove(QUEUE.size()-1);
    }

    /** Get the crate on top of the stack without popping it. */
    public static Crate peek() {
        return QUEUE.get(QUEUE.size()-1);
    }

    @Override
    public void run() {
        Crate crate = peek();
        //TODO: Change back to pop();
        if (crate == null) return;

        double seed = Math.random();
        if ((seed * crate.getRarity().rarity) * 100 > LIMITER) {
            Position pos = new Position(new int[] {50, 50});

            World world = pos.getWorld();
            Location loc = pos.getPos();

            Block block = world.getBlockAt(loc);
            block.setType(Material.CHEST);

            BlockState state = block.getState();
            if (state instanceof Chest) {
                Chest chest = (Chest) state;

                //TODO: Decide crate spawn using crate.decide();
                chest.getBlockInventory().setContents(null);
            }

            Bukkit.getPluginManager().callEvent(new CrateSpawnEvent(crate, pos.getPos()));
            //Bukkit.broadcastMessage(String.format("", null));
        }
    }
}
