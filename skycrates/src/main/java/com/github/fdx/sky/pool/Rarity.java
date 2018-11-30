package com.github.fdx.sky.pool;

/**
 * Describes the value of an item.
 * 
 * @author FlashDaggerX
 */
public enum Rarity {
    NIL(0), LESS(1.5), NORMAL(1.0), GREAT(0.5), GREATEST(0.25);

    public double rarity;
    Rarity(double rarity) { this.rarity = rarity; }

    /**
     * Returns Rarity from a chance value.
     * @param val The chance value.
     * @return The Rarity enum constant.
     */
    public Rarity fromRarity(double val) {
        for (Rarity v : values()) { if (v.rarity == val) return v; }
        return Rarity.NIL;
    }
}
