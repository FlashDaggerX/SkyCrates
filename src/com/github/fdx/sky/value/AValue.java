package com.github.fdx.sky.value;

/**
 * Abstract item worth.
 * 
 * @author FlashDaggerX
 */
public abstract class AValue {
    private Rarity worth;

    public AValue(Rarity worth) { this.worth = worth; }

    public void setRarity(Rarity worth) { this.worth = worth; }
    public Rarity getRarity() { return worth; }

    /**
     * Describes the value of an item.
     * 
     * @author FlashDaggerX
     */
    public enum Rarity {
        NIL(0), LESS(1.75), FIXED(1), NORMAL(1.5), GREAT(0.95), GREATEST(0.35);

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
}
