package com.github.fdx.sky.pool.scanner;

/** A function interface that allows the processing of JSON objects. */
@FunctionalInterface 
public interface Shell {

    /**
     * An event used for peeking at different tokens.
     * @param current The current token
     * 
     * @return 
     * {@code -1} Breaks document loop. <p></p>
     * {@code null} Do nothing. <p></p>
     * {@code Object} An evaluated object. It's added to the list.
     */
    public Object handleJSONToken(Token current); 
}
