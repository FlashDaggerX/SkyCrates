package com.github.fdx.sky.generator;

import java.util.HashMap;

import com.github.fdx.sky.treasure.Crate;
import com.github.fdx.sky.value.ISeedable;

import org.bukkit.scheduler.BukkitRunnable;

/** @author FlashDaggerX */
public class DeliveryQueue extends BukkitRunnable implements ISeedable {
	/** The period counted by the scheduler. */
    public static long PERIOD = 1000L;

    private static HashMap<Integer, Crate> QUEUE;

    public static void queue(Crate crate) { }

    @Override
    public void run() {}
}
