package com.paracamplus.bcm.simul;

public class WindowSimul {
	
	protected boolean isOpen;
	protected long lastOpen;
	private final int scenario;
	
	public  WindowSimul(int scenario) {
		this.scenario = scenario;
	}
	
	public void closeWindow() {
		isOpen = false;
	}
	public void openWindow() {
		isOpen = true;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public long getLastOpen() {
		return lastOpen;
	}
	
	// TODO : add accelerate time
	void scenario1() {
		long time = System.currentTimeMillis();
		if (isOpen) {
			if (time - lastOpen > 1000) {
				closeWindow();
			}
		}
		else {
			if (time - lastOpen > 1000) {
				openWindow();
				lastOpen = time;
			}
		}
	}
	
	void scenario2() {
		long time = System.currentTimeMillis();
		if (time > 2000) {
			closeWindow();
		}else
			isOpen = true;
	}

	// default
	public void routine() {
		switch (scenario) {
		case 1:
			scenario1();
			break;
		case 2:
			scenario2();
			break;
		default:
			scenario1();
		}
	}

}
