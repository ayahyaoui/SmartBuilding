package com.paracamplus.bcm.interfaces;

public interface RoomI {
	
	boolean hasMethod(String name);
	Object  execute(String name);
	public String getReflectionInboundPortURI();

}
