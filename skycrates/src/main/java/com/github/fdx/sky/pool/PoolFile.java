package com.github.fdx.sky.pool;

import java.io.File;
import java.io.IOException;

import com.github.fdx.sky.App;

import org.bukkit.configuration.file.YamlConfiguration;

public class PoolFile {
    private File poolFile;
    private YamlConfiguration yaml;

    private enum Maximum {X(0), Z(0); public int val; Maximum(int v) {this.val = v;}};
    
    public PoolFile() {
        this.poolFile = new File(App.dataFolder, "pool.yml");

        if (!this.poolFile.exists()) {
            try { 
                if (this.poolFile.createNewFile()) {
                    yaml = YamlConfiguration.loadConfiguration(this.poolFile);
                    
                    yaml.addDefault("x_max", 500);
                    yaml.addDefault("z_max", 500);
                    yaml.save(this.poolFile);
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
        
        // TODO: Why are you even using enums?
        Maximum.X.val = 0;
        Maximum.Z.val = 0;
    }
}
