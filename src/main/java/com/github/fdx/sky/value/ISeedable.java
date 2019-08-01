package com.github.fdx.sky.value;

import java.util.Random;

/** 
 * Seedable interface.
 * 
 * @author FlashDaggerX
 */
public interface ISeedable {
    // TODO: This shouldn't be here.
    public static final Random RAND = new Random();

    default Random rand() { return RAND; }
}
