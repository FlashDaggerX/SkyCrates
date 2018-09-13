package com.github.fdx.sky.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.pool.PoolFile;
import com.github.fdx.sky.pool.Treasure;
import com.google.common.collect.Lists;
import com.google.gson.stream.JsonReader;

/** @author FlashDaggerX */
public class GenerateCrate implements Generator {
    private PoolFile poolFile;

    private List<Treasure> items;
    private Double[] locations;

    public GenerateCrate(PoolFile file) {
        this.poolFile = file;
        this.items = new ArrayList<>();
        
        try {
            JsonReader reader = this.poolFile.createReader();
            
            // TODO: Not closing the reader continues the document scan from where it left off.
            this.locations = this.poolFile.getMaxAxis(reader);
            this.items = Lists.newArrayList(this.poolFile.getItems(reader, "defaultpool"));

            System.out.println(this.locations);
            reader.close();
		} catch (IOException e) { e.printStackTrace(); }
    }

	@Override
	public void randomize() {
		
	}

	@Override
	public int generate() {
		return 0;
	}
}
