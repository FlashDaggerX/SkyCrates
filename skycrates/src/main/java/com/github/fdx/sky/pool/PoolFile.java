package com.github.fdx.sky.pool;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import com.github.fdx.sky.App;
import com.github.fdx.sky.pool.Treasure.ItemValue;
import com.google.gson.stream.JsonWriter;

import org.bukkit.Material;

/**
 * Handles the JSON item pool.
 * 
 * @author FlashDaggerX
 * @see JsonWriter
 */
public class PoolFile implements Closeable {
    private File pool;
    private JsonWriter writer;

    public PoolFile() throws IOException {
        this.pool = new File(App.DATA, "pools.json");
        
        this.writer = new JsonWriter(
            new OutputStreamWriter(
                // TODO: Save to pool file after test.
                new PrintStream(System.out)
            )
        );
        this.writer.setIndent("    ");
        this.writer.setLenient(true);
        this.writer.setSerializeNulls(true);
        
        Pool p = new Pool("noobpool");
        p.add(Material.WOOD, ItemValue.LESS);
        p.add(Material.WATCH, ItemValue.LESS);

        if (!this.pool.exists()) {
            this.pool.createNewFile();

            writer.beginObject();
                writer.name("max-axis")
                .beginArray()
                    .value(50)
                    .value(-1)
                    .value(50)
                .endArray();

                writer.name("pools")
                .beginArray()
                    .beginObject()
                        .name("label").value(p.name())
                        .name("items")
                        .beginObject();
                            for (Treasure treasure : p.pool()) {
                                writer.name(treasure.material.name()).value(treasure.worth.name());
                            }
                        writer.endObject()
                    .endObject()
                .endArray();
            writer.endObject();
        }
    }

	@Override
	public void close() throws IOException {
        this.writer.flush();
		this.writer.close();
	}
} 
