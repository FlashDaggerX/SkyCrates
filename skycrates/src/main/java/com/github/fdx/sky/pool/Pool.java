package com.github.fdx.sky.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.fdx.sky.pool.Treasure.ItemValue;

import org.bukkit.Material;

/**
 * A builder for item pools.
 * 
 * @author FlashDaggerX
 */
public class Pool {
    private String name;

    private List<Treasure> pool;

    public Pool(String name) {
        this.name = name;

        this.pool = new ArrayList<>();
    }

    public void add(Material material, ItemValue worth) {
        this.pool.add(new Treasure(material, worth));
    }
    
    public void add(Treasure treasure) {
        this.pool.add(treasure);
    }

    public boolean del(Material material) {
        Iterator<Treasure> iter = this.pool.iterator();

        Treasure current;
        int index = 0;
        while (iter.hasNext()) {
            current = iter.next();

            if (current.material.equals(material)) break;

            index++;
        }

        if (!(index == this.pool.size())) {
            this.pool.remove(index);
            return true;
        }

        return false;
    }

    public String name() {
        return this.name;
    }

    public List<Treasure> pool() {
        return this.pool;
    }
}
