package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.DoorSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorDoorRecent extends AbstractSensor implements ISensor{
    
    protected boolean value;
    protected boolean nextValue;
    protected DoorSimul doorSim;
    protected final long marge;
    
    public SensorDoorRecent(AcceleratedClock clock, DoorSimul doorSim, long marge){
        super(clock);
        this.value = false;
        this.nextValue = false;
        this.doorSim = doorSim;
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
        nextValue = doorSim.isOpen() && doorSim.getLastOpen() + marge > getAccelerateClockTime();
    }
}
