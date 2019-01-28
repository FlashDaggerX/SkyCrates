package com.github.fdx.sky;

import java.io.File;

import com.github.fdx.sky.generator.CrateScheduler;
import com.github.fdx.sky.pool.Crate;
import com.github.fdx.sky.pool.Pool;
import com.github.fdx.sky.pool.Rarity;

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

        CrateScheduler.queue(new Crate(10, new Pool()
            .add(Material.CHAINMAIL_LEGGINGS, Rarity.LESS, 10)
            .add(Material.CHAINMAIL_BOOTS, Rarity.GREAT)
            .add(Material.CAULDRON, Rarity.NORMAL)));

        this.scheduler = new CrateScheduler().runTaskTimer(SkyCrates.INST, 0, CrateScheduler.PERIOD);
    }

    @Override
    public void onDisable() {
        scheduler.cancel();
    }

    void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
