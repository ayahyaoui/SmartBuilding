package com.paracamplus.bcm.sensor;

import java.time.Instant;

import com.paracamplus.bcm.simul.WindowSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorWindowRecent extends AbstractSensor implements ISensor{

    protected boolean value;
    protected boolean nextValue;
    protected WindowSimul windowSim;
    protected final long marge;

    public SensorWindowRecent(AcceleratedClock clock, WindowSimul windowSim, long marge){
        super(clock);
        this.value = false;
        this.nextValue = false;
        this.windowSim = windowSim;
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
        nextValue = windowSim.isOpen() && windowSim.getLastOpen() + marge > getAccelerateClockTime();
    }
}
