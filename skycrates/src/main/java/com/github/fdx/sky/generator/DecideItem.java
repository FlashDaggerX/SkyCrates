package com.github.fdx.sky.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.pool.PoolFile;
import com.github.fdx.sky.pool.Treasure;
import com.google.gson.stream.JsonReader;

/** @author FlashDaggerX */
public class DecideItem {
    private PoolFile poolFile;
    private List<Treasure> items;
    private Double[] locations;

    public DecideItem(PoolFile file) {
        this.poolFile = file;
        this.items = new ArrayList<>();
        
        try {
            JsonReader reader = this.poolFile.createReader();
            
            this.locations = this.poolFile.getMaxAxis(reader);
            this.items = null;

            System.out.println(this.locations);
            reader.close();
		} catch (IOException e) { e.printStackTrace(); }
    }
}
