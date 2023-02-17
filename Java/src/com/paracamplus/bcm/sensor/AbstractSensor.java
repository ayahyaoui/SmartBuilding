package com.paracamplus.bcm.sensor;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public abstract class AbstractSensor {
	protected final AcceleratedClock clock;
	
	public AbstractSensor(AcceleratedClock clock)
	{
		this.clock = clock;
	}

	public AcceleratedClock getClock() {
		return clock;
	}

	public long getAccelerateClockTime() {
		return clock.getStartInstant().toEpochMilli() + (long)(clock.getStartEpochNanos() * clock.getAccelerationFactor());
	}
	
}
	
	