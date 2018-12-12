package com.github.fdx.sky.io;

import java.io.File;
import java.io.IOException;

import com.github.fdx.sky.SkyCrates;

import org.bukkit.configuration.file.YamlConfiguration;

/** @author FlashDaggerX */
public class PluginConfig {
    public enum Types {
        X_MAX, X_MIN, Z_MAX, Z_MIN;
    }

    private File file;
    private YamlConfiguration config;

    public PluginConfig(String name) {
        this.file = new File(SkyCrates.DATA, name);

        if (!file.exists()) {
            try { file.createNewFile(); } 
            catch (IOException e) { e.printStackTrace(); }
        }

        this.config = new YamlConfiguration();
    }

    public void write(PluginConfig.Types type, Object val) {
        switch(type) {
            case X_MAX: config.set("x_max", val); break;
            case X_MIN: config.set("x_min", val); break; 
            case Z_MAX: config.set("z_max", val); break; 
            case Z_MIN: config.set("z_min", val); break;
        }
    }

    public Object read(PluginConfig.Types type) {
        switch(type) {
            case X_MAX: return config.get("x_max");
            case X_MIN: return config.get("x_min");
            case Z_MAX: return config.get("z_max");
            case Z_MIN: return config.get("z_min");
            default: return 1;
        }
    }

    public void save() {
        try { config.save(file); } 
        catch (IOException e) { e.printStackTrace(); }
    }
}
