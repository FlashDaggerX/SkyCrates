package com.github.fdx.sky.pool.scanner;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Jumper implements Closeable {
    private JsonReader read;

    public Jumper(JsonReader read) { this.read = read; }

    /** 
     * Scans the JSON document, without using Ctrl+C everywhere.
     * When scanning, use {@code Object.equals()} to compare objects.
     * 
     * @return A list of objects obtained while scanning the document.
     */
    public List<Object> jumpDocument(Shell handleObj) {
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

	@Override
	public void close() throws IOException {
		this.read.close();
	}
}
