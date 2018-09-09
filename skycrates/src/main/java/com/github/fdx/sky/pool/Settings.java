package com.github.fdx.sky.pool;

/** Keeps constant naming scheme for the PoolFiles. @author FlashDaggerX */
public enum Settings {
    MAXAXIS("max-axis"), POOLS("pools"), LABEL("label"), ITEMS("items");

    public String val;
    Settings(String val) { this.val = val; }
}
