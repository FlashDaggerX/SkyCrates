package com.github.fdx.sky.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.github.fdx.sky.App;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.bukkit.Bukkit;
import org.bukkit.Color;

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

        if (newFile) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                Color.RED + "Intentions were to create a new file. " +
                "Checking to see if it exists..."
            );

            if (this.pool.createNewFile()) writeNewFile(createWriter());
        }
    }

    public JsonWriter createWriter() throws FileNotFoundException {
        return new JsonWriter(
            new OutputStreamWriter(
                new FileOutputStream(this.pool)
            )
        );
    }

    public JsonReader createReader( ) throws FileNotFoundException {
        return new JsonReader(
            new InputStreamReader(
                new FileInputStream(this.pool)
            )
        );
    }

    public void getMaxAxis(JsonReader reader, char xyz) {
        scanDocument(reader, (token, current) -> {
            System.out.println(current);
        });
    }

    /** Scans the JSON document, without using Ctrl+C everywhere. */
    // TODO: Why does it stop at token six?
    private Object scanDocument(JsonReader reader, Shell handleObj) {
        reader.setLenient(true);
        
        int retval = -2;
        try {
            JsonToken token = null; Object current = -5;

            while (reader.hasNext()) {
                token = reader.peek();
                //if (token == JsonToken.END_DOCUMENT) break;

                switch(token) {
                    case BEGIN_OBJECT:  reader.beginObject(); retval = 0; break;
                    case BEGIN_ARRAY:   reader.beginArray(); retval = 1; break;
                    case NAME:          current = reader.nextName(); retval = 2; break;
                    case NUMBER:        current = reader.nextDouble(); retval = 3; break;
                    case BOOLEAN:       current = reader.nextBoolean(); retval = 4; break;
                    case STRING:        current = reader.nextString(); retval = 5; break;
                    case END_ARRAY:     reader.endArray(); retval = 6; break;
                    case END_OBJECT:    reader.endObject(); retval = 7; break;
                    default:            reader.skipValue(); retval = -1;
                }

                handleObj.doWithJSONObject(token, retval);
            }
        } catch (IOException e) { retval = -1; e.printStackTrace(); }

        return retval;
    }
    
    private void writeNewFile(JsonWriter writer) throws IOException {
        Bukkit.getServer().getConsoleSender().sendMessage(
            Color.AQUA + "Creating new pool with new mode."
        );
    
        writer.setIndent("    ");
    
        writer.beginObject();
            writer.name(Settings.MAXAXIS.val)
            .beginArray()
                .value(50.0).value(-1.0).value(50.0)
            .endArray();
    
            writer.name(Settings.POOLS.val)
            .beginArray()
                .beginObject()
                    .name(Settings.LABEL.val).value("test")
                    .name(Settings.ITEMS.val)
                    .beginObject()
                    .endObject()
                .endObject()
            .endArray();
        writer.endObject();
    
        writer.close();
    }
}

/** A function interface that allows the processing of JSON objects. */
@FunctionalInterface
interface Shell { public void doWithJSONObject(JsonToken token, Object current); }
