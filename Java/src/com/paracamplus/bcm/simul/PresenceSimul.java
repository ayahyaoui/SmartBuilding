package com.paracamplus.bcm.simul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class PresenceSimul {
    
    protected boolean isPresent;
    protected long lastPresence;
    private final AcceleratedClock clock;
    
    public  PresenceSimul(AcceleratedClock clock) {
        this.clock = clock;
        isPresent = false;
        lastPresence = -1;
    }
    
    public void setPresence(boolean b) {
        if (isPresent == b)
            return;
        isPresent = b;
        if (isPresent)
            lastPresence = clock.getStartInstant().toEpochMilli() + (long)(clock.getStartEpochNanos() * clock.getAccelerationFactor());
    }
    public void enter() {
        setPresence(true);
    }

    public void leave() {
        setPresence(false);
    }
    
    public boolean isPresent() {
        return isPresent;
    }
    
    public long getLastPresence() {
        return lastPresence;
    }
}