package com.github.fdx.sky.pool;

import java.io.File;
import java.io.IOException;

import com.github.fdx.sky.App;
import com.github.fdx.sky.pool.Pool.ItemValue;

import org.bukkit.Material;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PoolFile {
    private File poolFile;
    private YamlConfiguration yaml;
    
    public PoolFile() { 
        /*  TODO: Make sure the configuration is corrected if there's bad user input. */
        this.poolFile = new File(App.dataFolder, "pool.yml");

        if (!this.poolFile.exists()) {
            try { 
                if (this.poolFile.createNewFile()) {
                    yaml = YamlConfiguration.loadConfiguration(this.poolFile);

                    // TODO: Is this how paths work?
                    MemoryConfiguration m = new MemoryConfiguration();
                    m.addDefault("x_max", 500);
                    m.addDefault("z_max", 500);

                    Pool p = new Pool();
                    p   .add(Material.WOOD,     ItemValue.LOW)
                        .add(Material.WOOD_AXE, ItemValue.LOW)
                        .add(Material.WATCH,    ItemValue.LOW);
                    
                    m.createSection("pool", p.getPool());

                    yaml.addDefaults(m);
                }
            } catch (IOException e) { e.printStackTrace(); }
        } else yaml = YamlConfiguration.loadConfiguration(this.poolFile);
    }

    public void setXMax(int val) {
        yaml.set("x_max", val);
        save();
    }

    public void setZMax(int val) {
        yaml.set("z_max", val);
        save();
    }

    public int getXMax() {
        return yaml.getInt("x_max");
    }

    public int getZMax() {
        return yaml.getInt("z_max");
    }

    void save() {
        try {
            yaml.save(this.poolFile);
        } catch (IOException e) { e.printStackTrace(); };
    }
}
