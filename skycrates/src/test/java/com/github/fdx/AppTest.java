package com.github.fdx;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import com.google.gson.stream.JsonWriter;

import org.junit.Test;

public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        try {
            File f = File.createTempFile("yelm", ".json");
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(new PrintStream(f)));
            String[] ftl = {"Er", "Ef", "Ber", "Bef"};

            writer.setIndent("    ");
            writer.setLenient(true);
            writer.setSerializeNulls(true);

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
                        .name("label").value("noobpools")
                        .name("items")
                        .beginObject();
                            for (String s : ftl) {
                                writer.name(s).value(s);
                            } 
                        writer.endObject()
                    .endObject()
                .endArray();
            writer.endObject();

            writer.flush();
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
