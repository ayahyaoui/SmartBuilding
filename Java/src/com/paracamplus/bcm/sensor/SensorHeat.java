package com.paracamplus.bcm.sensor;

public class SensorHeat implements ISensor{
	protected float nextValue;
	protected float heat;



	SensorHeat(float heat){
		this.nextValue = 0;
		this.heat = heat;
	}

	@Override
	public void updateValue() {
		heat = nextValue;
	}

	@Override
	public void eval(java.time.Instant now) {
		nextValue = (float) (Math.sin(now.getEpochSecond())*10);
		System.out.println("Heat: " + nextValue + " " + now.getEpochSecond());
	}

	@Override
	public float getValue() {
		return heat;
	}

	@Override
	public void setValue(float f) {
		nextValue = f;
		
	}

}
