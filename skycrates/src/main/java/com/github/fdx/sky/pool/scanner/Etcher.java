package com.github.fdx.sky.pool.scanner;

import java.io.Closeable;
import java.io.IOException;

import com.github.fdx.sky.pool.Pool;
import com.github.fdx.sky.pool.Settings;
import com.github.fdx.sky.pool.Treasure;
import com.github.fdx.sky.pool.Treasure.ItemValue;
import com.google.gson.stream.JsonWriter;

import org.bukkit.Material;

public class Etcher implements Closeable {
    private JsonWriter writer;

    public Etcher(JsonWriter writer) { this.writer = writer; }

    /**
     * Write a default file.
     * 
     * @throws IOException
     */
    public void etchNewFile() throws IOException {
        writer.setIndent("    ");
        
        Pool defaultPool = new Pool("defaultPool");
        defaultPool.add(Material.WOOD, ItemValue.NORMAL, 5);
        defaultPool.add(Material.WATCH, ItemValue.NORMAL, 1);
        defaultPool.add(Material.DEAD_BUSH, ItemValue.WORTHLESS, 1);
        
        writer.beginObject();
            writer.name(Settings.MAXAXIS.val).beginArray()
                .value(50.0).value(-1.0).value(50.0)
            .endArray();
    
            writer.name(Settings.POOLS.val).beginArray()
                .beginObject()
                    .name(Settings.LABEL.val).value(defaultPool.name())
                    .name(Settings.ITEMS.val)
                    .beginArray();
                    
                    for (Treasure p : defaultPool.pool()) {
                        writer.beginArray()
                            .value(p.material.name())
                            .value(p.worth.rarity)
                            .value(p.quantity)
                        .endArray();
                    }

                    writer.endArray()
                .endObject()
            .endArray();
        writer.endObject();
    
        writer.close();
    }

	@Override
	public void close() throws IOException {
		this.writer.close();
	}
}
