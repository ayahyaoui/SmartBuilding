package com.paracamplus.bcm.sensor;

import java.time.Instant;

import com.paracamplus.bcm.simul.WindowSimul;

public class SensorWindowRecent implements ISensor{

    protected boolean value;
    protected boolean nextValue;
    protected WindowSimul windowSim;
    protected final int marge;

    SensorWindowRecent(WindowSimul windowSim, int marge){
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
	public void eval(Instant now){
        windowSim.routine();
        nextValue = windowSim.isOpen() && windowSim.getLastOpen() > marge;
    }

}
