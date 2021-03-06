package com.github.fdx.sky;

import java.io.File;

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
    }

    @Override
    public void onDisable() {
        scheduler.cancel();
    }

    private void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
