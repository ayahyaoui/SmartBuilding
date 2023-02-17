package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.DoorSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorDoorInstant extends AbstractSensor implements ISensor{

    protected boolean value;
    protected boolean nextValue;
    protected DoorSimul doorSim;
    
    public SensorDoorInstant(AcceleratedClock clock,DoorSimul doorSim){
        super(clock);
        this.value = doorSim.isOpen();
        this.nextValue = doorSim.isOpen();
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

    public void setValue(boolean b) {
        nextValue = b;
    }

    @Override
    public void eval() {
        nextValue = doorSim.isOpen();
    }

}
