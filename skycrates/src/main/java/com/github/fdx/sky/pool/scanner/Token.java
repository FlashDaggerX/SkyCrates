package com.github.fdx.sky.pool.scanner;

import com.google.gson.stream.JsonToken;

public class Token { 
    public JsonToken type; public Object obj; public String name;

    private char makeName;

    public Token() { this.makeName = 'a'; }

    public Token name(String name) { this.name = name; return this; }
    public Token obj(Object obj) { this.obj = obj; return this;}
    public Token type(JsonToken type) { this.type = type; return this;}

    /** A conditional character used in looping. */
    public char conName() { return this.makeName; }
    
    /** Sets the conditional character. */
    public void conName(char c) { this.makeName = c; }
}
