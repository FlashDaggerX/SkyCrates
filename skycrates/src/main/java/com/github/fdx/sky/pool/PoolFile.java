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

/** @author FlashDaggerX */
public class PoolFile {
    private File pool;
    private boolean useDoc;

    /** Handles the JSON Item Pool
     * 
     * @param mode The mode to open the file in.
     * @param newFile Are you creating a new file? (It can't already exist)
     */
    public PoolFile(String name, boolean newFile) throws IOException {
        this.pool = new File(App.DATA, name);
        this.useDoc = true;

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

    public void getMaxAxis(JsonReader reader, char xyz) {
        scanDocument(reader, (current) -> {
            System.out.println(current.obj + ":" + current.type);
        });
    }

    /** Stops scanning the document. */
    private void stopDocument() { this.useDoc = false; }

    /** Scans the JSON document, without using Ctrl+C everywhere. */
    private void scanDocument(JsonReader read, Shell handleObj) {
        read.setLenient(true);
        
        try {
            JsonToken token = JsonToken.NULL; Token current = new Token(token, 'T');

            while (token != JsonToken.END_DOCUMENT && this.useDoc) {

                token = read.peek();
                switch(token) {
                    case BEGIN_OBJECT:  current = new Token(token, '{'); read.beginObject(); break;
                    case BEGIN_ARRAY:   current = new Token(token, '['); read.beginArray(); break;
                    case NAME:          current = new Token(token, read.nextName()); break;
                    case NUMBER:        current = new Token(token, read.nextDouble()); break;
                    case BOOLEAN:       current = new Token(token, read.nextBoolean()); break;
                    case STRING:        current = new Token(token, read.nextString()); break;
                    case END_ARRAY:     current = new Token(token, ']'); read.endArray(); break;
                    case END_OBJECT:    current = new Token(token, '}'); read.endObject();; break;
                    default:            current = new Token(JsonToken.NULL, '~'); read.skipValue();
                }

                handleObj.doWithJSONObject(current);
            }

            this.useDoc = true;
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    private void writeNewFile(JsonWriter writer) throws IOException {
        writer.setIndent("    ");
    
        writer.beginObject();
            writer.name(Settings.MAXAXIS.val).beginArray()
                .value(50.0).value(-1.0).value(50.0)
            .endArray();
    
            writer.name(Settings.POOLS.val).beginArray()
                .beginObject()
                    .name(Settings.LABEL.val).value("test")
                    .name(Settings.ITEMS.val)
                    .beginObject().endObject()
                .endObject()
            .endArray();
        writer.endObject();
    
        writer.close();
    }
}

/** A function interface that allows the processing of JSON objects. */
@FunctionalInterface
interface Shell { public void doWithJSONObject(Token current); }

/** Describes an object from a PoolFile */
class Token { 
    public JsonToken type; public Object obj;

    Token(JsonToken type, Object obj) { this.type =  type; this.obj = obj; }
}
