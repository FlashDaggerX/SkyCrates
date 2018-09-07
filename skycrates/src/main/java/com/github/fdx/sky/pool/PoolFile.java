package com.github.fdx.sky.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.github.fdx.sky.App;
import com.github.fdx.sky.pool.Treasure.ItemValue;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;

/**
 * Handles the JSON item pool.
 * 
 * @author FlashDaggerX
 * @see JsonWriter
 */
public class PoolFile {
    public enum Mode {W, R, N};
    private PoolFile.Mode mode;

    private File pool;

    public PoolFile(PoolFile.Mode mode) throws IOException {
        this.mode = mode;
        this.pool = new File(App.DATA, "pools.json");

        System.out.println(this.pool.createNewFile());

        // TODO: Find a different way to scan for a new file; File.createNewFile() always comes out false?
        if (this.pool.exists() && getMode() == Mode.N) {
            writeNewFile(createWriter());
        }
    }

    private JsonWriter createWriter() throws FileNotFoundException {
        return new JsonWriter(
            new OutputStreamWriter(
                new FileOutputStream(this.pool)
            )
        );
    }

    private JsonReader createReader() throws FileNotFoundException {
        return new JsonReader(
            new InputStreamReader(
                new FileInputStream(this.pool)
            )
        );
    }

    private void writeNewFile(JsonWriter writer) throws IOException {
        Bukkit.getServer().getConsoleSender().sendMessage(
            Color.AQUA + "Creating pools.json with new mode."
        );

        writer.setIndent("    ");
        writer.setLenient(true);

        Pool p = new Pool("noobpool");
        p.add(Material.WOOD, ItemValue.LESS);
        p.add(Material.WATCH, ItemValue.LESS);

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
                    writer.endObject()
                .endObject()
            .endArray();
        writer.endObject();

        writer.close();
    }

	public PoolFile.Mode getMode() {
        return this.mode;
    }
} 
