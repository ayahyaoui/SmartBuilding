package com.paracamplus.bcm.simul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class HeatSimul {
    protected int heat;
    private final AcceleratedClock clock;
    
    public  HeatSimul(AcceleratedClock clock, int heat) {
        this.clock = clock;
        this.heat = heat;
    }
    
    public void setHeat(int heat) {
        this.heat = heat;
    }

    public int getHeat() {
        return heat;
    }

    public void increaseHeat(int heat) {
        this.heat += heat;
    }

    public void decreaseHeat(int heat) {
        this.heat -= heat;
    }

    
    
}
