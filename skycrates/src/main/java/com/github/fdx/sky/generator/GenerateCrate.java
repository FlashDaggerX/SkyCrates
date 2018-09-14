package com.github.fdx.sky.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.pool.PoolFile;
import com.github.fdx.sky.pool.Treasure;
import com.github.fdx.sky.pool.scanner.Jumper;

/** @author FlashDaggerX */
public class GenerateCrate implements Generator {
    private PoolFile poolFile;

    private List<Treasure> items;
    private Double[] locations;

    public GenerateCrate(PoolFile file) {
        this.poolFile = file;
        this.items = new ArrayList<>();
        
        try {
            Jumper jumper = new Jumper(this.poolFile.createReader());

            // TODO: Not closing the reader continues the document scan from where it left off.
            this.items = this.poolFile.getItems(jumper, "defaultpool");

            jumper.close();
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
