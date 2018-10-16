package com.github.fdx.sky.io;

import com.github.fdx.sky.pool.Pool;

public interface Config {
    enum SET { X_MIN, X_MAX, Z_MIN, Z_MAX }

    void setLocation(SET t, int val);
    int getLocation(SET t);

    void setItemPool(Pool p);
    Pool getItemPool();
}
