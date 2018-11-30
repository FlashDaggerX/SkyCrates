package com.github.fdx.sky.generator;

import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.pool.Crate;

import org.bukkit.scheduler.BukkitRunnable;

/** @author FlashDaggerX */
public class CrateScheduler extends BukkitRunnable {
    /** The refresh rate of the crate spawner. */
    public static final long PERIOD = 1000L;

    private static List<Crate> CRATES = new ArrayList<>();

    /** Crate spawning task. */
    public CrateScheduler() {}

	@Override
	public void run() {
		
	}
}
