package com.github.fdx.sky.pool;

import java.io.File;
import java.io.IOException;

import com.github.fdx.sky.App;
import com.github.fdx.sky.pool.Pool.ItemValue;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PoolFile {
    private File poolFile;
    private FileConfiguration yaml;
    
    public PoolFile(String name) {
        this.poolFile = new File(App.dataFolder, name);

        if (!this.poolFile.exists()) {
            this.poolFile.getParentFile().mkdirs();
        }

        try {
			if (this.poolFile.createNewFile()) {
                System.out.println(name + " doesn't exist. Creating...");
                yaml = new YamlConfiguration();

                MemoryConfiguration m = new MemoryConfiguration();
                m.addDefault("x-max", 500);
                m.addDefault("z-max", 500);

                Pool p = new Pool();
                p   .add(Material.WOOD,     ItemValue.LOW)
                    .add(Material.WOOD_AXE, ItemValue.LOW)
                    .add(Material.WATCH,    ItemValue.LOW);
                
                m.createSection("pool.default", p.getPool());

                m.options().copyDefaults(true);

                yaml.load(this.poolFile);
                yaml.setDefaults(m);
                yaml.options().copyDefaults(true);

                save();
            } else yaml = YamlConfiguration.loadConfiguration(this.poolFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
        }
    }

    public void setXMax(int val) {
        yaml.set("x-max", val);
        save();
    }

    public void setZMax(int val) {
        yaml.set("z-max", val);
        save();
    }

    public int getXMax() {
        return yaml.getInt("x-max");
    }

    public int getZMax() {
        return yaml.getInt("z-max");
    }

    private void save() {
        try {
            yaml.save(this.poolFile);
        } catch (IOException e) { e.printStackTrace(); };
    }
}
