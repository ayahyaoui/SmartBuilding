package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.DoorSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorDoorLocked extends AbstractSensor implements ISensor{
    
    protected boolean value;
    protected boolean nextValue;
    protected DoorSimul doorSim;
    
    public SensorDoorLocked(AcceleratedClock clock, DoorSimul doorSim){
        super(clock);
        this.value = false;
        this.nextValue = false;
        this.doorSim = doorSim;
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
        nextValue = doorSim.isLocked();
    }
}