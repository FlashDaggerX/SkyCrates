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
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;

/** @author FlashDaggerX */
public class PoolFile {
    private File pool;

    /** Handles the JSON Item Pool
     * 
     * @param mode The mode to open the file in.
     * @param newFile Are you creating a new file? (It can't already exist)
     */
    public PoolFile(String name, boolean newFile) throws IOException {
        this.pool = new File(App.DATA, name);

        System.out.println(this.pool.createNewFile());

        // TODO: Find a different way to scan for a new file; File.createNewFile() always comes out false?
        if (newFile) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                Color.RED + "Intentions were to create a new file. " +
                "Checking to see if it exists..."
            );

            if (this.pool.exists()) writeNewFile(createWriter());
        }
    }

    public JsonWriter createWriter() throws FileNotFoundException {
        return new JsonWriter(
            new OutputStreamWriter(
                new FileOutputStream(this.pool)
            )
        );
    }

    public JsonReader createReader() throws FileNotFoundException {
        return new JsonReader(
            new InputStreamReader(
                new FileInputStream(this.pool)
            )
        );
    }

    public int getMaxAxis(JsonReader reader, char xyz) {
        return 0;
    }

    private Object getObject(JsonReader reader, Object atToken) {
        try {
            JsonToken token; Object current;

            while (reader.hasNext()) {
                token = reader.peek();
                System.out.println(token);

                switch(token) {
                    case BEGIN_OBJECT:  reader.beginObject(); break;
                    case BEGIN_ARRAY:   reader.beginArray(); break;
                    case NAME:          current = reader.nextName(); break;
                    case NUMBER:        current = reader.nextDouble(); break;
                    case BOOLEAN:       current = reader.nextBoolean(); break;
                    case STRING:        current = reader.nextString(); break;
                    case END_ARRAY:     reader.endArray(); break;
                    case END_OBJECT:    reader.endObject(); break;
                    case END_DOCUMENT:  System.out.println("EOF"); break;
                    default:            current = -1;
                }
            }
        } catch (IOException e) { e.printStackTrace(); return 1; }

        return 0;
    }
    
    private void writeNewFile(JsonWriter writer) throws IOException {
        Bukkit.getServer().getConsoleSender().sendMessage(
            Color.AQUA + "Creating new pool with new mode."
        );
    
        writer.setIndent("    ");
        writer.setLenient(true);
    
        Pool p = new Pool("noobpool");
        p.add(Material.WOOD, ItemValue.LESS);
        p.add(Material.WATCH, ItemValue.LESS);
    
        writer.beginObject();
            writer.name(Settings.MAXAXIS.val)
            .beginArray()
                .value(50.0)
                .value(-1.0)
                .value(50.0)
            .endArray();
    
            writer.name(Settings.POOLS.val)
            .beginArray()
                .beginObject()
                    .name(Settings.LABEL.val).value(p.name())
                    .name(Settings.ITEMS.val)
                    .beginObject();
                    writer.endObject()
                .endObject()
            .endArray();
        writer.endObject();
    
        writer.close();
    }
} 
