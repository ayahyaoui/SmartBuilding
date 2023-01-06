package com.paracamplus.bcm.sensor;

public interface IFloatSensor extends ISensor {
	float getValue();
	void setValue(float f);
	void updateValue();
}
