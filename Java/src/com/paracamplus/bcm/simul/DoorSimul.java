package com.paracamplus.bcm.simul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class DoorSimul {
    protected boolean isOpen;
    protected boolean locked;
	protected long lastOpen;
	private final AcceleratedClock clock;
    
    public DoorSimul(AcceleratedClock clock) {
    	this.clock = clock;
    	isOpen = false;
    	lastOpen = -1;
        this.locked = false;
    }
    
    public void closeDoor() {
    	isOpen = false;
    	lastOpen = -1;
    }
    
    public void openDoor() {
    	if (isOpen || locked)
    		return;
    	isOpen = true;
    	lastOpen = clock.getStartInstant().toEpochMilli() + (long)(clock.getStartEpochNanos() * clock.getAccelerationFactor());
    }

    public void lockDoor() {
        this.locked = true;
    }

    public void unlockDoor() {
        this.locked = false;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isOpen() {
    	return isOpen;
    }
    
    public long getLastOpen() {
    	return lastOpen;
    }

}
