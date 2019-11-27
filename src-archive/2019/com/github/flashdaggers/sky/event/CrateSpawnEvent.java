package com.github.fdx.sky.event;

import com.github.fdx.sky.treasure.Crate;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrateSpawnEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    private Crate crate;
    private Location loc;

    public CrateSpawnEvent(Crate crate, Location loc) {
        this.crate = crate;
        this.loc = loc;
    }

    public Crate getCrate() { 
        return crate; 
    }
    
    public Location getLocation() { 
        return loc; 
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
	}
}
