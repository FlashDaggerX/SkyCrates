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
public class App extends JavaPlugin {
    /** The plugin instance. */
    public static App INST;
    /** The plugin's data folder, as defined by Spigot. */
    public static File DATA;

    private BukkitTask scheduler;

    public App() {}

    @Override
    public void onEnable() {
        App.INST = this;
        App.DATA = getDataFolder();

        if (!App.DATA.exists()) App.DATA.mkdir();

        CrateScheduler.queue(new Crate(10, new Pool()
            .add(Material.APPLE, Rarity.LESS, 10)
            .add(Material.CHAINMAIL_BOOTS, Rarity.GREAT)
            .add(Material.CAULDRON, Rarity.NORMAL)));

        this.scheduler = new CrateScheduler().runTaskTimer(App.INST, 0L, CrateScheduler.PERIOD);
    }

    @Override
    public void onDisable() {
        scheduler.cancel();
    }

    void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
