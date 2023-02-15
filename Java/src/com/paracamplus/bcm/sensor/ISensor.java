package com.paracamplus.bcm.sensor;

public interface ISensor {
	void updateValue();
	void eval(java.time.Instant now);
	Object getValue();	
}
