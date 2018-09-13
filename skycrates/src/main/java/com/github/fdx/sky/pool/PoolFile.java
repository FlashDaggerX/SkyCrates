package com.github.fdx.sky.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.github.fdx.sky.App;
import com.github.fdx.sky.pool.Treasure.ItemValue;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

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

        if (newFile) {
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

    public Double[] getMaxAxis(JsonReader reader) {
        List<Object> loc = scanDocument(reader, 
        (current) -> {
            if (current.name.equalsIgnoreCase(Settings.MAXAXIS.val)) {
                if (current.type.equals(JsonToken.NUMBER)) {
                    return current.obj;
                } else if (current.type.equals(JsonToken.END_ARRAY)) {
                    return -1;
                }
            }
            return null;
        });

        return loc.toArray(new Double[loc.size()]);
    }

    public Treasure[] getItems(JsonReader reader, String poolName) {
        List<Object> items = scanDocument(reader,
        (current) -> {
            System.out.println(current.type + ":" + current.obj + "[" + current.name + "]");
            return null;
        });

        return items.toArray(new Treasure[items.size()]);
    }

    /** 
     * Scans the JSON document, without using Ctrl+C everywhere.
     * When scanning, use {@code Object.equals()} to compare objects.
     * 
     * @return A list of objects obtained while scanning the document.
     */
    private List<Object> scanDocument(JsonReader read, Shell handleObj) {
        read.setLenient(true);
        
        List<Object> retobj = new ArrayList<>();

        try {
            JsonToken token = JsonToken.NULL; 
            Token current = new Token().type(token).name("NULL");
            Object listen = null;   // The object returned by handleJSONToken()

            while (token != JsonToken.END_DOCUMENT) {
                token = read.peek(); current.obj(null);

                switch(token) {
                    case BEGIN_OBJECT:  current.type(token); read.beginObject(); break;
                    case BEGIN_ARRAY:   current.type(token); read.beginArray(); break;
                    case NAME:          current.type(token).name(read.nextName()); break;
                    
                    case NUMBER:        current.type(token).obj(read.nextDouble()); break;
                    case BOOLEAN:       current.type(token).obj(read.nextBoolean()); break;
                    case STRING:        current.type(token).obj(read.nextString()); break;
                    
                    case END_ARRAY:     current.type(token); read.endArray(); break;
                    case END_OBJECT:    current.type(token); read.endObject(); break;
                    default:            current.type(JsonToken.NULL); read.skipValue(); break;
                } 
                
                if ((listen = handleObj.handleJSONToken(current)) != null) {
                    if (listen.equals(-1)) break;
                    
                    retobj.add(listen);
                }
            }
            
        } catch (IOException e) { e.printStackTrace(); }

        return retobj;
    }
    
    private void writeNewFile(JsonWriter writer) throws IOException {
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
                    .name(Settings.LABEL.val).value("test")
                    .name(Settings.ITEMS.val)
                    .beginObject();
                    
                    for (Treasure p : defaultPool.pool()) {
                        writer.name(p.material.name()).beginArray()
                            .value(p.worth.rarity)
                            .value(p.quantity)
                        .endArray();
                    }

                    writer.endObject()
                .endObject()
            .endArray();
        writer.endObject();
    
        writer.close();
    }
}

/** A function interface that allows the processing of JSON objects. */
@FunctionalInterface 
interface Shell {

    /**
     * An event used for peeking at different tokens.
     * @param current The current token
     * 
     * @return 
     * {@code -1} Breaks document loop. <p></p>
     * {@code null} Do nothing. <p></p>
     * {@code Object} An evaluated object. It's added to the list.
     */
    public Object handleJSONToken(Token current); 
}

/** Describes a JsonToken. */
class Token { 
    public JsonToken type; public Object obj; String name;

    Token() {}

    public Token name(String name) { this.name = name; return this; }
    public Token obj(Object obj) { this.obj = obj; return this;}
    public Token type(JsonToken type) { this.type = type; return this;}
}
