package com.github.fdx.sky;

import java.io.File;

import com.github.fdx.sky.generator.DeliveryQueue;
import com.github.fdx.sky.treasure.Crate;
import com.github.fdx.sky.treasure.Treasure;
import com.github.fdx.sky.value.AValue.Rarity;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/** @author FlashDaggerX */
public class SkyCrates extends JavaPlugin {
    /** The plugin instance. */
    public static SkyCrates INST;

    /** The plugin's data folder, as defined by Spigot. */
    public static File DATA;

    private BukkitTask scheduler;

    public SkyCrates() {}

    @Override
    public void onEnable() {
        SkyCrates.INST = this;
        SkyCrates.DATA = getDataFolder();

        if (!SkyCrates.DATA.exists()) SkyCrates.DATA.mkdir();

        DeliveryQueue.push(new Crate(Rarity.NORMAL));
        DeliveryQueue.peek().populate(new Treasure[] { 
            new Treasure(Material.ACACIA_DOOR, Rarity.FIXED),
            new Treasure(Material.DIAMOND_AXE, Rarity.GREAT, 1, 50)
        });

        this.scheduler = new DeliveryQueue().runTaskTimer(
            SkyCrates.INST, 0, DeliveryQueue.PERIOD);
    }

    @Override
    public void onDisable() {
        scheduler.cancel();
    }

    private void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
