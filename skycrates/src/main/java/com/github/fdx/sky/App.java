package com.github.fdx.sky;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    public static App instance;
    public static File dataFolder;

    public App() {}

    @Override
    public void onEnable() {
        App.instance = this;
        App.dataFolder = getDataFolder();
    }

    @Override
    public void onDisable() {}

    void registerCmd(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }
}
