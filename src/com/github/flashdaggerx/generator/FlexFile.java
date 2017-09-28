package com.github.flashdaggerx.generator;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.flashdaggerx.Sky;

public class FlexFile {
	private File file;

	private YamlConfiguration poolList;

	private int x_max, z_max;

	public void refreshConfiguration() {
		file = new File(Sky.getInstance().getDataFolder(), "pools.yml");
		poolList = YamlConfiguration.loadConfiguration(file);
		x_max = Sky.getInstance().getConfig().getInt("x-max");
		z_max = Sky.getInstance().getConfig().getInt("z-max");
	}

	public File getFile() {
		return file;
	}

	public YamlConfiguration getPoolList() {
		return poolList;
	}

	public int getX_max() {
		return x_max;
	}

	public int getZ_max() {
		return z_max;
	}
}
