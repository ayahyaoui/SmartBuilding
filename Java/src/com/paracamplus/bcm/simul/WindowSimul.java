package com.paracamplus.bcm.simul;
import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;


public class WindowSimul {
	
	protected boolean isOpen;
	protected long lastOpen;
	private final AcceleratedClock clock;
	
	public  WindowSimul(AcceleratedClock clock) {
		this.clock = clock;
		isOpen = false;
		lastOpen = -1;
	}
	
	public void closeWindow() {
		isOpen = false;
		lastOpen = -1;
	}

	public void openWindow() {
		if (isOpen)
			return;
		isOpen = true;
		lastOpen = clock.getStartInstant().toEpochMilli() + (long)(clock.getStartEpochNanos() * clock.getAccelerationFactor());
	}
	public boolean isOpen() {
		return isOpen;
	}
	public long getLastOpen() {
		return lastOpen;
	}
}
