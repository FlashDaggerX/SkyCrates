package com.github.fdx.sky;

import java.io.File;
import java.io.IOException;

import com.github.fdx.sky.generator.GenerateCrate;
import com.github.fdx.sky.pool.PoolReader;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

/** @author FlashDaggerX */
public class App extends JavaPlugin {
    /** The plugin instance. */
    public static App INST;
    /** The plugin's data folder, as defined by Spigot. */
    public static File DATA;

    public App() {}

    @Override
    public void onEnable() {
        App.INST = this;
        App.DATA = getDataFolder();

        if (!App.DATA.exists()) App.DATA.mkdir();

        try {
            new GenerateCrate(new PoolReader("defaultpool.json", true));
		} catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void onDisable() {}

    void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
