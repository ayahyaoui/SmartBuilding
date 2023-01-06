package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.WindowSimul;

public class SensorWindowInstant implements IBoolSensor{

	protected boolean value;
	protected boolean nextValue;
	protected WindowSimul windowSim;
	
	SensorWindowInstant(WindowSimul windowSim){
		this.value = false;
		this.nextValue = false;
		this.windowSim = windowSim;
	}

	@Override
	public void updateValue() {
		value = nextValue;
	}

	@Override
	public boolean getValue() {
		return value;
	}

	
	public void setValue(boolean b) {
		nextValue = b;
	}

	@Override
	public void eval() {
		windowSim.routine();
		nextValue = windowSim.isOpen();
	}
    

}
