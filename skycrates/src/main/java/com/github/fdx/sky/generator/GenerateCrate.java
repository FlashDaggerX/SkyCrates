package com.github.fdx.sky.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.pool.PoolReader;
import com.github.fdx.sky.pool.Treasure;
import com.github.fdx.sky.pool.scanner.Jumper;

/** @author FlashDaggerX */
public class GenerateCrate implements Generator {
    private PoolReader readPool;

    private List<Treasure> items;
    private Double[] locations;

    public GenerateCrate(PoolReader file) {
        this.readPool = file;
        this.items = new ArrayList<>();
        
        try {
            Jumper jumper = this.readPool.createJumper();

            // TODO: Not closing the reader continues the document scan from where it left off.
            this.items = this.readPool.getItems(jumper, "defaultpool");

            for (Treasure t : this.items) {
                System.out.println(t.material + ";" + t.worth);
            }

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
