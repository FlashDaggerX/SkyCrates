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
import com.github.fdx.sky.pool.scanner.Etcher;
import com.github.fdx.sky.pool.scanner.Jumper;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.bukkit.Material;

/** @author FlashDaggerX */
public class PoolReader {
    private File pool;

    /** Handles the JSON Item Pool
     * 
     * @param mode The mode to open the file in.
     * @param newFile Are you creating a new file? (It can't already exist)
     */
    public PoolReader(String name, boolean newFile) throws IOException {
        this.pool = new File(App.DATA, name);

        if (newFile) {
            if (this.pool.createNewFile()) createEtcher().etchNewFile();
        }
    }

    public Etcher createEtcher() throws FileNotFoundException {
        return new Etcher(
            new JsonWriter(
                new OutputStreamWriter(
                    new FileOutputStream(this.pool)
                )
            )
        );
    }

    public Jumper createJumper() throws FileNotFoundException {
        return new Jumper(
            new JsonReader(
                new InputStreamReader(
                    new FileInputStream(this.pool)
                )
            )
        );
    }

    public Double[] getMaxAxis(Jumper jump) {
        List<Object> loc = jump.jumpDocument
        ((current) -> {
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

    public List<Treasure> getItems(Jumper jump, String poolName) {
        List<Object> items = jump.jumpDocument
        ((current) -> {
            if (current.name.equalsIgnoreCase(Settings.LABEL.val)) {
                if (current.obj.equals(poolName)) current.conName('l');
            }

            if (current.name.equalsIgnoreCase(Settings.ITEMS.val)) {
                if (current.conName() == 'l') {
                    switch (current.type) {
                        case NUMBER: return current.obj;
                        case STRING: return current.obj;
                        default: return null;
                    }
                }
            }
            return null;
        });

        List<Treasure> treasure = new ArrayList<>();

        for (int i = 0; i < (items.size() / 3); i+=3) {
            treasure.add(new Treasure(
                Material.getMaterial(items.get(i).toString()), 
                ItemValue.NORMAL.fromRarity((Integer) items.get(i+2)), 
                (Integer) items.get(i+3))
            );
        }

        return treasure;
    }
}
