package com.paracamplus.bcm.simul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class LightSimul {  
    protected boolean isOn;

    private final AcceleratedClock clock;
    
    public LightSimul(AcceleratedClock clock) {
    	this.clock = clock;
    	isOn = false;
 
    }
    
    public void turnOff() {
    	isOn = false;
    }
    
    public void turnOn() {
    	isOn = true;
    }
    
    public boolean isOn() {
    	return isOn;
    }
    
    
}
