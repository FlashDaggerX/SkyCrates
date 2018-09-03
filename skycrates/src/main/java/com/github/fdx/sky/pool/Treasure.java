package com.github.fdx.sky.pool;

import org.bukkit.Material;

public class Treasure {
    public enum ItemValue {WORTHLESS, LESS, NORMAL, GREAT, GREATEST}

    public Material material;
    public ItemValue worth;

    public Treasure(Material material, ItemValue worth) {
        this.material = material;
        this.worth = worth;
    }
}
