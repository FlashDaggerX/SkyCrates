package com.github.fdx.sky.generator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.github.fdx.sky.pool.Crate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.scheduler.BukkitRunnable;

/** @author FlashDaggerX */
public class CrateScheduler extends BukkitRunnable {
    // TODO: Two seconds?
    /** The refresh rate of the crate spawner. */
    public static final long PERIOD = 1000;

    private static Queue<Crate> CRATES = new LinkedList<>();
    // TODO: You have a crate ID tracker so you can fetch spawn times. 
    // It only returns references.
    private static HashMap<Integer, Crate> ID = new HashMap<>();

    /**
     * Queues a {@link Crate} to be spawned into the world.
     * @param crate The reference to the crate.
     * @return Was the crate added to the queue?
     */
    public static boolean queue(Crate crate) {
        if (CRATES.add(crate) && !ID.containsKey(crate.id)) {
            ID.putIfAbsent(crate.id, crate);
            return true;
        }
        
        return false;
    }

    /**
     * Removes and returns the head of the stack.
     * @return The removed {@link Crate}
     */
    public static Crate remove() {
        ID.remove(CrateScheduler.peek().id);
        return CRATES.remove();
    }

    /**
     * Gives a glimpse of the next crate on the stack.
     * @return The next {@link Crate}
     */
    public static Crate peek() {
        return CRATES.peek();
    }

    /**
     * Retrieves and removes the next crate on the stack.
     * @return The next {@link Crate}
     */
    protected static Crate poll() {
        return CRATES.poll();
    }

	@Override
	public void run() {
        Crate crate = CrateScheduler.peek();
        if (crate == null) return;
        //ID.remove(crate.id);
        double seed = new Random().nextDouble();

        if ((seed * crate.worth.rarity) * 100 > Items.LIMITER) {
            Items items = new Items(crate.getPool());
            Position pos = new Position(new int[]{500, 500});
    
            World world = pos.getWorld();
            Location loc = pos.getPos();

            Block block = world.getBlockAt(loc);
            block.setType(Material.CHEST);

            BlockState state = block.getState();
            if (state instanceof Chest) {
                Chest chest = (Chest) state;
                chest.getBlockInventory().setContents(items.decideItems());
            } else {
                Bukkit.broadcastMessage(
                    ChatColor.RED + "There was a problem spawning crate. [ID] " + crate.id);
                return;
            }

            Bukkit.broadcastMessage(
                ChatColor.DARK_BLUE + "A crate has spawned at " +
                ChatColor.DARK_GREEN + loc.getBlockX() + ':' + loc.getBlockY() + ':' + loc.getBlockZ() + "! " +
                ChatColor.RED + "[ID] " + crate.id);
        } else {
            Bukkit.broadcastMessage(
                    ChatColor.RED + "Crate didn't spawn. [ID] " + crate.id);
        }
    }
}
