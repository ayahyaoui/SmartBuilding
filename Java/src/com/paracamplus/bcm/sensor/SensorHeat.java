package com.paracamplus.bcm.sensor;

public class SensorHeat implements IFloatSensor{
	protected float nextValue;
	protected float heat;


	SensorHeat(float heat){
		this.nextValue = 0;
		this.heat = heat;
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValue(float f) {
		// TODO Auto-generated method stub
		
	}

}
