package com.github.flashdaggerx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Sky extends JavaPlugin {

	private static Sky inst;
	private CommandExecutor cmd;

	private HashMap<String, Object> defaults;
	private List<String> defPool, defPoolLoc;

	private File file;
	private FileConfiguration pools;
	
	@Override
	public void onEnable() {
		inst = this;
		inst.getServer().getConsoleSender().sendMessage(
				ChatColor.YELLOW + "[SkyCrates] Server Version: " + Bukkit.getVersion());

		performFileCreation();
		
		if (Bukkit.getVersion().equalsIgnoreCase("git-Spigot-21fe707-e1ebe52 (MC: 1.8.8)")) {
			cmd = new com.github.flashdaggerx.generator.v1_8.Commands();
		} else if (Bukkit.getVersion().equalsIgnoreCase("git-Spigot-3fb9445-6e3cec8 (MC: 1.11.2)")) {
			cmd = new com.github.flashdaggerx.generator.v1_11.Commands();
		}
		inst.getCommand("skycrates").setExecutor(cmd);
		inst.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SkyCrates] Completed.");
	}

	public static Sky getInstance() {
		return inst;
	}

	private void performFileCreation() {
		if (!inst.getDataFolder().exists() || inst.getConfig().getBoolean("reset")) {
			defaults = new HashMap<>();
			defaults.put("x-max", 500);
			defaults.put("z-max", 500);
			inst.getConfig().addDefaults(defaults);
			inst.getConfig().options().copyDefaults(true);
			inst.saveDefaultConfig();

			file = new File(inst.getDataFolder(), "pools.yml");
			pools = YamlConfiguration.loadConfiguration(file);
			defPool = new ArrayList<String>();
			defPool.add("diamond");
			pools.set("pool.get", defPool);

			defPoolLoc = new ArrayList<String>();
			defPoolLoc.add("world");
			defPoolLoc.add("0");
			defPoolLoc.add("120");
			defPoolLoc.add("0");
			pools.set("pool.locations.test", defPoolLoc);
			pools.options().copyDefaults(true);

			try {
				pools.save(file);
			} catch (IOException e) {
				e.printStackTrace();
				inst.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "[SkyCrates] There was an error: " + e.getMessage());
			}
			defPool.clear();
			defPoolLoc.clear();

			if (inst.getConfig().getBoolean("reset")) {
				inst.getServer().getConsoleSender()
						.sendMessage(ChatColor.YELLOW + "[SkyCrates] The config has been reset!");
				inst.getConfig().set("reset", false);
			} else {
				inst.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "[SkyCrates] The config has been created!");
			}
		}
	}

}
