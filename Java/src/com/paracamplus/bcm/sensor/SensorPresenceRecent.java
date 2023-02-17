package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.PresenceSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorPresenceRecent extends AbstractSensor implements ISensor{
    
    protected boolean value;
    protected boolean nextValue;
    protected PresenceSimul presenceSim;
    protected final long marge;
    
    public SensorPresenceRecent(AcceleratedClock clock, PresenceSimul presenceSim, long marge){
        super(clock);
        this.value = false;
        this.nextValue = false;
        this.presenceSim = presenceSim;
        this.marge = marge;
    }

    @Override
    public void updateValue() {
        value = nextValue;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void eval(){
        nextValue = presenceSim.isPresent() && presenceSim.getLastPresence() + marge > getAccelerateClockTime();
    }
    
}
