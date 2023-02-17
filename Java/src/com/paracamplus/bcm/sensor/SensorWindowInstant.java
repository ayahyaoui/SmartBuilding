package com.paracamplus.bcm.sensor;

import java.time.Instant;

import com.paracamplus.bcm.simul.WindowSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorWindowInstant  extends AbstractSensor implements ISensor{

	protected boolean value;
	protected boolean nextValue;
	protected WindowSimul windowSim;
	
	public SensorWindowInstant(AcceleratedClock clock, WindowSimul windowSim){
		super(clock);
		this.value = windowSim.isOpen();
		this.nextValue = windowSim.isOpen();
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
	public void eval() {
		nextValue = windowSim.isOpen();
	}

}
