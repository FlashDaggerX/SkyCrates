package com.github.fdx.sky.io;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.github.fdx.sky.pool.Pool;
import com.github.fdx.sky.pool.Treasure;

import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/** @author FlashDaggerX */
public class CrateConfig {
    enum SET { X_MIN, X_MAX, Z_MIN, Z_MAX }
    
    private File file;
    private MemoryConfiguration config;

    /**
     * Creates a configuration file for pools.
     * @param f The file to use.
     * @param c The type of configuration back-end to use.
     * Using MemoryConfiguration stores it into memory, not a file!
     */
    public CrateConfig(File f, Class<?> c) { 
        this.file = f;
        
        if (c.equals(MemoryConfiguration.class)) {
            this.config = new MemoryConfiguration();
        } else if (c.equals(YamlConfiguration.class)) {
            // TODO: Figure out how to save from a MemoryConfig
            this.config = new YamlConfiguration();
        }
    }
    
    public void setLimit(SET t, int val) {
        config.set(t.name(), val);
    }

    public int getLimit(SET t) {
        return config.getInt(t.name());
    }

    public void setSeed(int val) {
        config.set("seed", val);
    }

    public int getSeed() { 
        return config.getInt("seed"); 
    }

    public void setPool(Pool p) {
        Map<String, Double[]> items = new HashMap<>();

        /* In the treasure's value array, quantity is read first. */
        for (Treasure t : p.pool()) {
            items.put(
                t.material.toString(), 
                new Double[] {t.quantity, t.worth.rarity}
            );
        }

        config.createSection("pool", items);
    }

    public Pool getPool() { 
        return null; 
    }

    public void swap(MemoryConfiguration config) {
        this.config = config;
    }

    public void copy(char red, MemoryConfiguration defaults) {
        switch (red) {
            case '<':
                this.config.addDefaults(defaults);
                this.config.options().copyDefaults(true);
            break;
            case '>':
                defaults.addDefaults(this.config);
                defaults.options().copyDefaults(true);
            break;
        }
    }

    public String poolName() { return file.getName(); }
}
