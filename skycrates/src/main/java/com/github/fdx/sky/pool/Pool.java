package com.github.fdx.sky.pool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Material;

/**
 * A builder for item pools.
 * 
 * @author FlashDaggerX
 */
public class Pool {
    public enum ItemValue {LOW, NORMAL, GREAT, HIGH};
    
    private HashMap<Material, String> items;
    private String name;
    
    public Pool(String name) {
        this.name = name;
        this.items = new HashMap<>();
    }
    
    public Pool add(Material item, Pool.ItemValue val) {
        items.put(item, val.name());
        return this;
    }

    public Pool del(Object item) {
        items.remove(item);
        return this;
    }

    public Set<Material> getItems() {
        return items.keySet();
    }

    public Collection<String> getItemValues() {
        return items.values();
    }

    public int getIndex(Object item) {
        Iterator<Material> i = getItems().iterator();
        int index = 0;

        while (i.hasNext()) {
            if (i.next().name().equals(item)) {
                break;
            }

            index++;
        }

        return index;
    }

    public String getName() {
        return this.name;
    }

    @Deprecated
    public HashMap<Material, String> getPool() {
        return this.items;
    }
}
