package com.github.fdx;

import java.io.IOException;
import java.io.OutputStreamWriter;

import com.google.gson.stream.JsonWriter;

import org.junit.Test;

public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));

        try {
            writer.setIndent("  ");
            writer.setLenient(true);

            writer.beginObject();
            writer.name("test1");

            writer.beginArray();
            writer.value(500);
            writer.value(50);
            writer.endArray();

            writer.endObject();

            writer.close();
        } catch (IOException e) {}
    }
}
