package com.github.fdx.sky.generator;

/** @author FlashDaggerX */
public interface Generator {

    /** Randomizes the items decided during generation. */
    void randomize();

    /** Begins the crate generation, and spawns it into the world. */
    int generate();
}
