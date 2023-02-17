package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.PresenceSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorPresenceInstant extends AbstractSensor implements ISensor{

    protected boolean value;
    protected boolean nextValue;
    protected PresenceSimul presenceSim;
    
    public SensorPresenceInstant(AcceleratedClock clock, PresenceSimul presenceSim){
        super(clock);
        this.value = presenceSim.isPresent();
        this.nextValue = presenceSim.isPresent();
        this.presenceSim = presenceSim;
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
        nextValue = presenceSim.isPresent();
    }

}