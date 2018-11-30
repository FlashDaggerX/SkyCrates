package com.github.fdx.sky;

import java.io.File;

import com.github.fdx.sky.generator.CrateScheduler;

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

        this.scheduler = new CrateScheduler().runTaskTimer(App.INST, 0L, CrateScheduler.PERIOD);
    }

    @Override
    public void onDisable() {}

    void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
