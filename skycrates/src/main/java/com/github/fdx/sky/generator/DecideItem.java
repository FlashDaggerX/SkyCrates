package com.github.fdx.sky.generator;

import java.io.IOException;

import com.github.fdx.sky.pool.PoolFile;
import com.google.gson.stream.JsonReader;

/** @author FlashDaggerX */
public class DecideItem {
    private PoolFile poolFile;
    //private List<Treasure> items;

    public DecideItem(PoolFile file) {
        this.poolFile = file;
        
        try {
            JsonReader reader = this.poolFile.createReader();

            this.poolFile.getMaxAxis(reader, 'x');

            reader.close();
		} catch (IOException e) { e.printStackTrace(); }
    }
}
