package com.paracamplus.bcm.sensor;

import org.antlr.v4.parse.ANTLRParser.prequelConstruct_return;

import com.paracamplus.bcm.simul.HeatSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorHeat extends AbstractSensor implements ISensor{
	protected int nextValue;
	protected int value;
	protected AcceleratedClock clock;
	protected HeatSimul heatSim;


	SensorHeat(AcceleratedClock clock, 		HeatSimul heatSim){
		super(clock);
		this.clock = clock;
		this.heatSim = heatSim;
		this.value = heatSim.getHeat();
	}

	@Override
	public void updateValue() {
		value = nextValue;
	}

	@Override
	public void eval() {
		nextValue = heatSim.getHeat();
	}

	@Override
	public Integer getValue() {
		return value;
	}
}
