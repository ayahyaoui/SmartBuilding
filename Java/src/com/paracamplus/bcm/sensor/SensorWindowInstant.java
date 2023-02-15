package com.paracamplus.bcm.sensor;

import java.time.Instant;

import com.paracamplus.bcm.simul.WindowSimul;

public class SensorWindowInstant implements ISensor{

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
	public Boolean getValue() {
		return value;
	}

	
	public void setValue(boolean b) {
		nextValue = b;
	}



	@Override
	public void eval(Instant now) {
		
		
	}
    

}
