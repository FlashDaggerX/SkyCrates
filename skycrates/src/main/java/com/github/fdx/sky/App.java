package com.github.fdx.sky;

import java.io.File;
import java.io.IOException;

import com.github.fdx.sky.pool.PoolFile;
import com.github.fdx.sky.pool.PoolFile.Mode;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    /** The plugin instance. */
    public static App INST;
    /** The plugin's data folder, as defined by Spigot. */
    public static File DATA;

    private PoolFile pool;

    public App() {}

    @Override
    public void onEnable() {
        App.INST = this;
        App.DATA = getDataFolder();

        if (!App.DATA.exists()) App.DATA.mkdir();

        try {
            pool = new PoolFile(Mode.N);
		} catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void onDisable() {}

    void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
